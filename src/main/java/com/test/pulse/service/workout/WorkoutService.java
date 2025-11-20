package com.test.pulse.service.workout;

import java.io.File;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.pulse.mapper.WorkoutMapper;
import com.test.pulse.model.course.CoordinateDTO;
import com.test.pulse.model.workout.WorkoutDTO;
import com.test.pulse.model.workout.WorkoutLogDTO;
import com.test.pulse.service.course.util.CourseCalc;

import lombok.RequiredArgsConstructor;
import me.himanshusoni.gpxparser.GPXParser;
import me.himanshusoni.gpxparser.modal.GPX;
import me.himanshusoni.gpxparser.modal.Track;
import me.himanshusoni.gpxparser.modal.TrackSegment;
import me.himanshusoni.gpxparser.modal.Waypoint;

/**
 * 운동 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class WorkoutService {

	private final WorkoutMapper workoutMapper;
    private final CourseCalc courseCalc;        
    private final ObjectMapper objectMapper;    
    private final GPXParser gpxParser = new GPXParser();
	/**
	 * 여러 개의 GPX 파일을 운동 기록으로 저장한다.
	 * @param gpxFiles GPX 파일 리스트
	 * @param accountId 사용자 ID
	 * @param userWeight 사용자 체중
	 * @param attachment 첨부 파일
	 * @param exerciseComment 운동 코멘트
	 * @param request HttpServletRequest 객체
	 * @return 저장된 운동 기록 리스트
	 * @throws Exception exception
	 */
    @Transactional
	public List<WorkoutDTO> saveWorkoutLogs(List<MultipartFile> gpxFiles, String accountId, double userWeight,
			MultipartFile attachment, String exerciseComment, HttpServletRequest request) throws Exception {
		
		List<WorkoutDTO> savedLogs = new ArrayList<>();
		String attachmentUrl = null;
		if (attachment != null && !attachment.isEmpty()) {

            // 1. 파일을 저장할 서버의 *실제 물리 경로* 찾기
            // (servlet-context.xml의 <resources mapping="/asset/**" ...> 경로 활용)
            ServletContext context = request.getServletContext();
            String saveDirectory = context.getRealPath("/asset/uploads/workout");

            // 2. 저장 폴더가 없으면 생성 (mkdirs)
            File dir = new File(saveDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 3. 고유한 파일명 생성 (UUID 사용, 덮어쓰기 방지)
            // (예: 2a9b..._my_photo.jpg)
            String originalFilename = StringUtils.cleanPath(attachment.getOriginalFilename());
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            // 4. 파일을 실제 경로에 저장
            File destinationFile = new File(saveDirectory, uniqueFilename);
            attachment.transferTo(destinationFile);

            // 5. (중요) DB에는 브라우저가 접근할 수 있는 *웹 경로*를 저장
            attachmentUrl = request.getContextPath() + "/asset/uploads/workout/" + uniqueFilename;
        }
		
		for (MultipartFile gpxFile : gpxFiles) {
			List<Waypoint> wayPoints = parseGpxTrackPoints(gpxFile);
			if (wayPoints.isEmpty()) continue;
			
			//계산용 좌표 리스트 변환
			List<CoordinateDTO> coords = convertPointsToCoords(wayPoints);
			
			//핵심 데이터 계산
			//1. 거리(km)
			double totalDistance = courseCalc.calculateTotalDistance(coords) / 1000.0;
			//2. 시간(초), 운동 날짜
			Date startTimeDate = wayPoints.get(0).getTime();
			Date endTimeDate = wayPoints.get(wayPoints.size()-1).getTime();
			Instant startInstant = startTimeDate.toInstant();
			Instant endInstant = endTimeDate.toInstant();
			
			long totalTimeInSeconds = Duration.between(startInstant, endInstant).getSeconds();
			LocalDate workoutDate = startInstant.atZone(ZoneId.systemDefault()).toLocalDate();
			
			//3. 평균 페이스(분/km)
			double avgPace = 0.0;
			if(totalDistance>0) {
				avgPace = (totalTimeInSeconds/60.0) / totalDistance;
			}
			//4. 칼로리
			int totalCalories = calculateCalories(avgPace, totalTimeInSeconds, userWeight);
			
			System.out.println("------[DB에 저장할 계산 결과 로그]------");
			System.out.println("totalDistance: " + totalDistance);
			System.out.println("totalTimeInSeconds: " + totalTimeInSeconds);
			System.out.println("workoutDate: " + workoutDate);
			System.out.println("avgPace: " + avgPace);
			System.out.println("totalCalories: " + totalCalories);
			System.out.println("----------------------------------------");
			
			//DTO에 세팅
			WorkoutDTO log = new WorkoutDTO();
			log.setAccountId(accountId);
			log.setTotalDistance(totalDistance);
			log.setTotalTime(totalTimeInSeconds);
			log.setAvgPace(avgPace);
			log.setTotalCalories(totalCalories);
			log.setWorkoutDate(workoutDate.toString());
			log.setTrackData(objectMapper.writeValueAsString(coords)); //json
			log.setAttachment(attachmentUrl);
			log.setExerciseComment(exerciseComment);
			
			//첨부파일, 코멘트 체크 필요
			
			//DB에 저장
			workoutMapper.insertWorkout(log);
			savedLogs.add(log);
		}
		
		return savedLogs;
	}
	
	/**
	 * 사용자의 운동 기록 목록을 조회한다.
	 * @param accountId 사용자 ID
	 * @return 운동 기록 목록
	 */
	public List<WorkoutLogDTO> getMyLogs(String accountId) {
		return workoutMapper.getWorkoutsByAccountId(accountId);
	}
	
	/**
	 * 소모 칼로리를 계산하기 위한 헬퍼 메서드
	 * @param avgPace 평균 페이스
	 * @param totalTimeInSeconds 총 운동 시간 (초)
	 * @param userWeight 사용자 체중
	 * @return 소모 칼로리
	 */
	private int calculateCalories(double avgPace, long totalTimeInSeconds, double weightKg) {
		//(간단한 METs 계산 예시: 페이스별로 강도 부여)
		double mets = 0;
        if (avgPace <= 4.0) mets = 14.0; // 4분 페이스 (매우 고강도)
        else if (avgPace <= 5.0) mets = 10.5; // 5분 페이스
        else if (avgPace <= 6.0) mets = 8.5; // 6분 페이스
        else mets = 7.0; // 7분 이상 (보통 걷기/조깅)
        
        double hours = totalTimeInSeconds / 3600.0;
        // 공식: (METs * 3.5 * 체중(kg) / 200) * 시간(분)
        return (int) (mets * weightKg * hours);
	}

	/**
	 * GPX 파일을 파싱하기 위한 헬퍼 메서드
	 * @param gpxFile GPX 파일
	 * @return 웨이포인트 리스트
	 */
	private List<Waypoint> parseGpxTrackPoints(MultipartFile gpxFile) throws Exception {
		List<Waypoint> trackPoints = new ArrayList<>();
	    try (InputStream in = gpxFile.getInputStream()){
	        GPX gpx = gpxParser.parseGPX(in);
	        if(gpx != null && gpx.getTracks() != null) {
	            for (Track track : gpx.getTracks()) {
	                for (TrackSegment segment : track.getTrackSegments()) {
	                    trackPoints.addAll(segment.getWaypoints()); 
	                }
	            }
	        }
	    } 
	    return trackPoints;
	}

	/**
	 * 웨이포인트 리스트를 좌표 리스트로 변환하기 위한 헬퍼 메서드
	 * @param trackPoints 웨이포인트 리스트
	 * @return 좌표 리스트
	 */
	private List<CoordinateDTO> convertPointsToCoords(List<Waypoint> trackPoints) {
	    List<CoordinateDTO> coords = new ArrayList<>();
	    for (Waypoint wp : trackPoints) {
	    	CoordinateDTO dto = new CoordinateDTO();
	        dto.setLat(wp.getLatitude());
	        dto.setLon(wp.getLongitude());
	        coords.add(dto);
	    }
	    return coords;
	}

	/**
	 * 운동 기록 상세 정보를 조회한다.
	 * @param workoutSeq 운동 기록 번호
	 * @return 운동 기록 상세 정보
	 */
	@Transactional(readOnly = true)
	public WorkoutDTO getWorkoutDetail(int workoutSeq) {
		
		return workoutMapper.getWorkoutDetail(workoutSeq);
	}
	

}

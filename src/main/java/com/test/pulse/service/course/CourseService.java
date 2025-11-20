package com.test.pulse.service.course;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.pulse.mapper.CourseMapper;
import com.test.pulse.model.course.CoordinateDTO;
import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.model.course.GPXCourseDTO;
import com.test.pulse.model.course.PageDTO;
import com.test.pulse.service.api.OpenStreetMapAPIService;
import com.test.pulse.service.course.util.CourseCalc;

import lombok.RequiredArgsConstructor;
import me.himanshusoni.gpxparser.GPXParser;
import me.himanshusoni.gpxparser.modal.GPX;
import me.himanshusoni.gpxparser.modal.Track;
import me.himanshusoni.gpxparser.modal.TrackSegment;
import me.himanshusoni.gpxparser.modal.Waypoint;

/**
 * 코스 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class CourseService {
	private final CourseMapper courseMapper; //주입
	private final ObjectMapper objectMapper;
	private final OpenStreetMapAPIService mapapiservice;
	private final CourseCalc courseCalculator;
	
	private final GPXParser gpxParser = new GPXParser();
	
	/**
	 * 사용자가 gpx 파일을 첨부해서 코스 등록을 요청하는 경우를 처리한다.
	 * @param gpxFile GPX 파일
	 * @param courseName 코스 이름
	 * @param description 코스 설명
	 * @param accountId 사용자 ID
	 * @return 생성된 코스 정보
	 * @throws Exception
	 */
	public GPXCourseDTO parseAndSaveGpxCourse(
			MultipartFile gpxFile, 
			String courseName, 
			String description, 
			String accountId) throws Exception {
		
		List<CoordinateDTO> coords = parseGpxFile(gpxFile);
		
		return saveCourse(coords, courseName, description, accountId);
		
		
	}
	
	/**
	 * DB에 코스를 저장하는 작업을 담당한다.
	 * @param coords 좌표 리스트
	 * @param courseName 코스 이름
	 * @param description 코스 설명
	 * @param accountId 사용자 ID
	 * @return 저장된 코스 정보
	 * @throws Exception
	 */
	@Transactional
	public GPXCourseDTO saveCourse(
			List<CoordinateDTO> coords, 
			String courseName, 
			String description, 
			String accountId) throws Exception {
		//2. 데이터 계산
		double courseLengthInMeters = courseCalculator.calculateTotalDistance(coords);
		
		String trackDataJson = objectMapper.writeValueAsString(coords);
		
		//3. 주소 변환(API 연동 후 추가 작업 완료)
		CoordinateDTO startCoord = coords.get(0);
        CoordinateDTO endCoord = coords.get(coords.size() - 1);
        
        String startAddress = mapapiservice.coordToAddress(startCoord.getLat(), startCoord.getLon()); 
        String endAddress = mapapiservice.coordToAddress(endCoord.getLat(), endCoord.getLon());   
		
        //4. DTO세팅
        GPXCourseDTO course = new GPXCourseDTO();
        course.setCourseName(courseName);     // (폼에서 받은 값)
        course.setDescription(description); // (폼에서 받은 값)
        course.setAccountId(accountId);     // (폼에서 받은 값)
        
        course.setCourseLength(courseLengthInMeters / 1000.0); // km 단위로 변환 저장
        course.setTrackData(trackDataJson); // (계산된 JSON)
        course.setStartAddress(startAddress); // (변환된 주소)
        course.setEndAddress(endAddress);   // (변환된 주소)
        
        //5. DB작업(인터페이스매퍼)
        courseMapper.insertCourse(course);
        
		return course;
		
	}

	/**
	 * gpx 파일이 첨부된 경우 사용하는 헬퍼 메서드
	 * @param gpxFile GPX 파일
	 * @return 좌표 리스트
	 * @throws Exception
	 */
	private List<CoordinateDTO> parseGpxFile(MultipartFile gpxFile) throws Exception {
		List<CoordinateDTO> coords = new ArrayList<>();
		try (InputStream in = gpxFile.getInputStream()){
			
			GPX gpx = gpxParser.parseGPX(in);
			System.out.println("[DEBUG] " + gpx);
			
			if(gpx!=null &&gpx.getTracks() !=null) {
				for (Track track : gpx.getTracks()) {
                    for (TrackSegment segment : track.getTrackSegments()) {
                        for (Waypoint point : segment.getWaypoints()) { 
                            // (CoordinateDTO는 lat, lon을 가진 단순 DTO 클래스)
                        	CoordinateDTO dto = new CoordinateDTO();
                        	dto.setLat(point.getLatitude());
                        	dto.setLon(point.getLongitude());
                            coords.add(dto);
                        }
                    }
                }
			}
		} 
		return coords;
	}

	/**
	 * 코스 상세 정보를 조회한다.
	 * @param courseSeq 상세보기할 코스의 PK
	 * @return 코스 상세 정보
	 */
	@Transactional(readOnly = true) // (읽기 전용)
	public GPXCourseDTO getCourseDetail(int courseSeq) {
		
		GPXCourseDTO course = courseMapper.getCourseDetail(courseSeq);
		
		return course;
	}
	

//	public List<CourseCardDTO> getAllCourses() {
//		
//		return courseMapper.getAllCourses();
//	}

	/**
	 * 페이징 처리된 코스 목록을 조회한다.
	 * @param page 페이지 번호
	 * @param keyword 검색 키워드
	 * @return 코스 목록과 페이징 정보를 담은 Map
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getCourseListPage(int page, String keyword) {
		Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
		//1. 전체 코스 개수 조회
		int totalCount = courseMapper.getTotalCourseCount(params);
		
		//2. 페이징 계산
		PageDTO pagedto = new PageDTO(totalCount, page);
		
		//3. 
		int startRow = ((page-1)* pagedto.getPageSize()) + 1;
		int endRow = startRow + pagedto.getPageSize() - 1;
		
		params.put("startRow", startRow);
		params.put("endRow", endRow);

		List<CourseCardDTO> courselist = courseMapper.getAllCourses(params);
		
		Map<String, Object> data = new HashMap<>();
		data.put("courseList", courselist);
		data.put("pageDTO", pagedto);
		
		return data;
	}

//	@Transactional(readOnly = true)
//	public Map<String, Object> getCourseMainPage(String accountId) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		
//		
//		return null;
//	}

	/**
	 * 인기 코스 목록을 조회한다.
	 * @param count 조회할 개수
	 * @return 인기 코스 목록
	 */
	@Transactional(readOnly = true)
	public List<CourseCardDTO> getPopularCourses(int count) {	
		return courseMapper.getPopularCourses(count);
	}

	/**
	 * 사용자의 위치를 기반으로 추천 코스 목록을 조회한다.
	 * @param accountId 사용자 ID
	 * @param count 조회할 개수
	 * @return 추천 코스 목록
	 */
	@Transactional(readOnly = true)
	public List<CourseCardDTO> getRecommendedCourses(String accountId, int count) {
		Map<String, String> location = courseMapper.getUserLocation(accountId);
		
		if (location != null) {
            String county = location.get("county");   // 예: "강남구"
            String district = location.get("district"); // 예: "역삼동"
            
            // 2. [핵심] 주소 전처리 ("구", "동" 제거)
            // 이유: DB에 "서울 강남 역삼"으로 저장되어 있을 수도 있고, 
            // "서울시 강남구 역삼동"으로 저장되어 있을 수도 있습니다.
            // "강남", "역삼" 키워드로 검색해야 검색 적중률(Hit Rate)이 높아집니다.
            if (county != null) county = county.replace("구", ""); // "강남구" -> "강남"
            if (district != null) district = district.replace("동", ""); // "역삼동" -> "역삼"

            // 3. Mapper에 전달할 파라미터 생성
            Map<String, Object> params = new HashMap<>();
            params.put("county", county);
            params.put("district", district);
            params.put("count", count);

            // 4. 추천 코스 목록 조회
            return courseMapper.getRecommendedCourses(params);
        }
		
		return null;
	}
	
	
}

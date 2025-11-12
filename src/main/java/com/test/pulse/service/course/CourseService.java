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

@Service
@RequiredArgsConstructor
public class CourseService {
	private final CourseMapper courseMapper; //주입
	private final ObjectMapper objectMapper;
	private final OpenStreetMapAPIService mapapiservice;
	private final CourseCalc courseCalculator;
	
	private final GPXParser gpxParser = new GPXParser();
	
	/**
	 * 사용자가 gpx 파일을 첨부해서 코스 등록을 요청하는 경우
	 * @param gpxFile
	 * @param courseName
	 * @param description
	 * @param accountId
	 * @return
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
	 * DB에 저장하는 작업 담당
	 * @param coords
	 * @param courseName
	 * @param description
	 * @param accountId
	 * @return
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
	 * gpx 파일 첨부한 경우 사용하는 헬퍼 메서드
	 * @param gpxFile
	 * @return
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
	 * 코스 상세보기
	 * @param courseSeq 상세보기할 코스의 PK
	 * @return
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
	 * 코스 목록 보기(페이징 처리 포함)
	 * @param page
	 * @param keyword 
	 * @return
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

}

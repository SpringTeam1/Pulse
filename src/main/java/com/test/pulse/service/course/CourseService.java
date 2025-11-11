package com.test.pulse.service.course;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.pulse.mapper.CourseMapper;
import com.test.pulse.model.course.CoordinateDTO;
import com.test.pulse.model.course.CourseDTO;

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
	
	public CourseDTO parseAndSaveGpxCourse(
			MultipartFile gpxFile, 
			String courseName, 
			String description, 
			String accountId) throws Exception {
		
		List<CoordinateDTO> coords = new ArrayList<>();
		try (InputStream in = gpxFile.getInputStream()){
			
			GPXParser parser = new GPXParser();
			GPX gpx = parser.parseGPX(in);
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
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//2. 데이터 계산
		double courseLengthInMeters = calculateTotalDistance(coords);
		
		String trackDataJson = objectMapper.writeValueAsString(coords);
		
		//3. 주소 변환(Kakao API 연동 후 추가 작업 필요 TODO)
		CoordinateDTO startCoord = coords.get(0);
        CoordinateDTO endCoord = coords.get(coords.size() - 1);
        
        String startAddress = "임시 시작 주소"; 
        // TODO: kakaoAPIService.coordToAddress(startCoord.getLat(), startCoord.getLon());
        String endAddress = "임시 종료 주소";   
        // TODO: kakaoAPIService.coordToAddress(endCoord.getLat(), endCoord.getLon());
		
        //4. DTO세팅
        CourseDTO course = new CourseDTO();
        course.setCourseName(courseName);     // (폼에서 받은 값)
        course.setDescription(description); // (폼에서 받은 값)
        course.setAccountId(accountId);     // (폼에서 받은 값)
        
        course.setCourseLength(courseLengthInMeters / 1000.0); // (예: km 단위로 변환 저장)
        course.setTrackData(trackDataJson); // (계산된 JSON)
        course.setStartAddress(startAddress); // (변환된 주소)
        course.setEndAddress(endAddress);   // (변환된 주소)
        
        //5. DB작업(인터페이스매퍼)
        courseMapper.insertCourse(course);
        
		return course;
	}

	private double calculateTotalDistance(List<CoordinateDTO> coords) {
		double totalDistance = 0.0;
        // 0번부터 마지막-1번까지 순회
        for (int i = 0; i < coords.size() - 1; i++) {
            CoordinateDTO start = coords.get(i);
            CoordinateDTO end = coords.get(i + 1);
            // 두 점 사이의 거리를 계속 더함
            totalDistance += haversineDistance(start.getLat(), start.getLon(), end.getLat(), end.getLon());
        }
        return totalDistance;
	}

	private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // 지구 반지름 (km)
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c * 1000; // m 단위로 변환
	}
	
	
}

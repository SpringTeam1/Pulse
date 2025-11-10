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
			MultipartFile courseName, 
			MultipartFile description, 
			MultipartFile accountId) throws Exception {
		
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
		
		// --2. 데이터 계산
		
		
		return null;
	}
	
	
}

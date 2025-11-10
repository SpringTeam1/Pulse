package com.test.pulse.restcontroller.course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.model.course.CourseDTO;
import com.test.pulse.service.course.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseRestController {
	private final CourseService courseService;
	
	//코스 등록 요청하기(파일첨부)
	@PostMapping
	public ResponseEntity<CourseDTO> registerCourse(
		@RequestParam("gpxFile") MultipartFile gpxFile,
		@RequestParam("courseName") String courseName,
		@RequestParam("description") String description,
		@RequestParam("accountId") String accountId
	) {
		try {
			CourseDTO savedCourse = courseService.parseAndSaveGpxCourse(gpxFile, courseName, description, accountId);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

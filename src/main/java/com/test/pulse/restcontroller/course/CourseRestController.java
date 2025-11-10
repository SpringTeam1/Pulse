package com.test.pulse.restcontroller.course;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			
		) {
		
		
		return null;
	}
}

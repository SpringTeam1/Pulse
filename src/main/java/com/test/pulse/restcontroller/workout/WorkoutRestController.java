package com.test.pulse.restcontroller.workout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.pulse.service.course.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutRestController {
	private final CourseService courseService;
	private final Logger log = LoggerFactory.getLogger(CourseService.class);
	
	
	
}

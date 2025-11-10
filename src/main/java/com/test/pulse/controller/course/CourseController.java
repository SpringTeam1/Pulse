package com.test.pulse.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {
	@GetMapping("/register")
	public String gpxRegPage() {
		return "course.courseregister";
	}
	
	
}

package com.test.pulse.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.service.course.CourseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final CourseService courseService;
	
	/**
	 * admin 메인 페이지 호출
	 * @param model
	 * @return
	 */
	@GetMapping("/admin")
	public String header(Model model) {
		List<CourseCardDTO> top3Courses = courseService.getPopularCourses(3);
		model.addAttribute("top3Courses", top3Courses);
		return "script.admin.admin";
	}
}

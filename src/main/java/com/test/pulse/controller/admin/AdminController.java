package com.test.pulse.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.service.course.CourseService;

import lombok.RequiredArgsConstructor;

/**
 * 관리자 페이지 관련 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final CourseService courseService;
	
	/**
	 * 관리자 메인 페이지를 반환한다.
	 * 인기 코스 상위 3개를 조회하여 모델에 추가한다.
	 *
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @return 관리자 메인 페이지 뷰 이름
	 */
	@GetMapping("/admin")
	public String header(Model model) {
		List<CourseCardDTO> top3Courses = courseService.getPopularCourses(3);
		model.addAttribute("top3Courses", top3Courses);
		return "script.admin.admin";
	}
	
	/**
	 * 관리자 코스 관리 페이지를 반환한다.
	 *
	 * @return 관리자 코스 관리 페이지 뷰 이름
	 */
	@GetMapping("/admin/course")
	public String adminCourse() {
		
		return "admin.admincourse";
	}
	
}
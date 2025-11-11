package com.test.pulse.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {
	
	/**
	 * 코스 메인페이지
	 * @return
	 */
	@GetMapping("/main")
	public String courseMain() {
		return "course.coursemain";
	}
	
	/**
	 * 사용자가 gpx를 첨부하여 코스 등록 요청
	 * @return
	 */
	@GetMapping("/gpxregister")
	public String gpxRegPage() {
		return "script.course.gpxcourseregister";
	}
	/**
	 * 사용자가 직접 지도 좌표를 찍어서 코스 등록 요청
	 * @return
	 */
	@GetMapping("/manualregister")
	public String manualRegPage() {
		return "script.course.manualcourseregister";
	}
	
	
	
}

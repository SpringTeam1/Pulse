package com.test.pulse.controller.course;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.model.course.GPXCourseDTO;
import com.test.pulse.service.course.CourseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
	
	private final CourseService courseService; //주입
	
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
	
	/**
	 * 코스 상세보기
	 * @param courseSeq
	 * @param model
	 * @return
	 */
	@GetMapping("/detail/{courseSeq}")
	public String courseDetail(
		@PathVariable("courseSeq") int courseSeq,
		Model model //jsp로 데이터를 전달할 Model
	) {
		GPXCourseDTO course = courseService.getCourseDetail(courseSeq);
		model.addAttribute("course", course);
		return "script.course.coursedetail";
	}
	
	/**
	 * 코스 전체 목록 보기
	 * @param model
	 * @return
	 */
	@GetMapping("/list") 
	public String courseList(@RequestParam(defaultValue = "1") int page, Model model){
		Map<String, Object> data = courseService.getCourseListPage(page);
		
		//List<CourseCardDTO> courseList = courseService.getAllCourses();
		model.addAttribute("courseList", data.get("courseList"));
		model.addAttribute("pageDTO", data.get("pageDTO"));
		return "course.courselist";
	}
	
}

package com.test.pulse.controller.course;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
	public String courseMain(Model model, HttpSession session) {
		// TODO [테스트용 하드코딩] 로그인한 척 세션에 아이디 심기
        // (실제 로그인 기능 구현 전까지만 사용하고 나중에 삭제하세요)
        String accountId = "test4@naver.com"; // DB에 실제 존재하는 아이디여야 주소를 가져옵니다.
        session.setAttribute("accountId", accountId);
		
		// 1. 세션에서 아이디 가져오기 (없으면 null)
        // (Spring Security @AuthenticationPrincipal 사용 시 변경 가능)
        accountId = (String) session.getAttribute("accountId");
        
        // 인기 코스 조회
        int popularLimit = (accountId == null) ? 6 : 3;
        List<CourseCardDTO> popularList = courseService.getPopularCourses(popularLimit);
        model.addAttribute("popularList", popularList);
        
        // 추천코스 조회(로그인 사용자일 경우)
        if (accountId != null) {
            List<CourseCardDTO> recommendedList = courseService.getRecommendedCourses(accountId, 3);
            model.addAttribute("recommendedList", recommendedList);
        }
        
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
	 * 코스 전체 목록 보기, 메인페이지 겸용
	 * @param model
	 * @return
	 */
	@GetMapping("/list") 
	public String courseList(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "") String keyword,
			Model model){
		Map<String, Object> data = courseService.getCourseListPage(page, keyword);
		
		//List<CourseCardDTO> courseList = courseService.getAllCourses();
		model.addAttribute("courseList", data.get("courseList"));
		model.addAttribute("pageDTO", data.get("pageDTO"));
		model.addAttribute("keyword", keyword);
		
		return "course.courselist";
	}
	
}

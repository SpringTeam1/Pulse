package com.test.pulse.controller.course;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.model.course.GPXCourseDTO;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.course.CourseService;

import lombok.RequiredArgsConstructor;

/**
 * 코스 관련 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
	
	private final CourseService courseService; //주입
	
	/**
	 * 코스 메인 페이지를 반환한다.
	 * 로그인 상태에 따라 인기 코스와 추천 코스를 조회하여 모델에 추가한다.
	 *
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @param auth  Authentication 객체
	 * @return 코스 메인 페이지 뷰 이름
	 */
	@GetMapping("/main")
	public String courseMain(Model model, Authentication auth) {

		String accountId = null;
		
		if (auth != null && auth.isAuthenticated()) {
			Object principal = auth.getPrincipal();
			
			if (principal instanceof CustomUser) {
				CustomUser customUser = (CustomUser) principal;
				accountId = customUser.getAdto().getAccountId();
			}
		}
        
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
	 * GPX 파일을 첨부하여 코스를 등록하는 페이지를 반환한다.
	 * @return GPX 코스 등록 페이지 뷰 이름
	 */
	@GetMapping("/gpxregister")
	public String gpxRegPage() {
		return "script.course.gpxcourseregister";
	}
	/**
	 * 지도에 직접 좌표를 찍어 코스를 등록하는 페이지를 반환한다.
	 * @return 수동 코스 등록 페이지 뷰 이름
	 */
	@GetMapping("/manualregister")
	public String manualRegPage() {
		return "script.course.manualcourseregister";
	}
	
	/**
	 * 코스 상세 정보를 보여주는 페이지를 반환한다.
	 * @param courseSeq 조회할 코스의 번호
	 * @param model     뷰에 전달할 데이터를 담는 Model 객체
	 * @return 코스 상세 페이지 뷰 이름
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
	 * 코스 전체 목록을 보여주는 페이지를 반환한다.
	 * @param page    요청된 페이지 번호 (기본값: 1)
	 * @param keyword 검색 키워드
	 * @param model   뷰에 전달할 데이터를 담는 Model 객체
	 * @return 코스 목록 페이지 뷰 이름
	 */
	@GetMapping("/list") 
	public String courseList(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "") String keyword,
			Model model){
		Map<String, Object> data = courseService.getCourseListPage(page, keyword);
		
		model.addAttribute("courseList", data.get("courseList"));
		model.addAttribute("pageDTO", data.get("pageDTO"));
		model.addAttribute("keyword", keyword);
		
		return "course.courselist";
	}
	
}
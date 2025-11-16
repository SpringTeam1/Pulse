package com.test.pulse.controller.mainpage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.service.course.CourseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainPageController {
	
	private final CourseService courseService; //couseService 주입

    @GetMapping("/index.do")
    public String mainPage(Model model) {
    	
    	// 페이지 로딩 시 인기 코스 3개를 조회하여 Model에 담습니다.
        List<CourseCardDTO> popularCourses = courseService.getPopularCourses(3);
        model.addAttribute("popularCourses", popularCourses);
    	
        return "main.index"; // WEB-INF/views/main/index.jsp
    }
}

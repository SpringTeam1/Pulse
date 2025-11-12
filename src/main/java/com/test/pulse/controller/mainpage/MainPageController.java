package com.test.pulse.controller.mainpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/index.do")
    public String mainPage() {
        return "main.index"; // WEB-INF/views/main/index.jsp
    }
}

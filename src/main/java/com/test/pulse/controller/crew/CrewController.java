package com.test.pulse.controller.crew;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrewController {

    @GetMapping("/crewmain")
    public String crewMain(){


        return "crew.main";
    }
	
	
}

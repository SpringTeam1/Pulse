package com.test.pulse.controller.crew;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pulse.service.crew.CrewService;

@Controller
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @GetMapping("/crewmain")
    public String showCrewMainPage(
            @RequestParam(value = "lat", required = false, defaultValue = "37.5665") double lat,
            @RequestParam(value = "lng", required = false, defaultValue = "126.9780") double lng,
            Model model) {

        model.addAttribute("nearbyCrewList", crewService.getNearbyCrews(lat, lng));
        model.addAttribute("popularCrewList", crewService.getPopularCrews());
        return "crew.main";
    }


    @GetMapping("/crewregister")
    public String showCrewRegisterPage(){

        return "crew.register";
    }

}
package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.service.crew.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crew")
public class CrewRestController {

    private final CrewService crewService;



}

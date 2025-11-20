package com.test.pulse.restcontroller.mainpage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.pulse.model.mainpage.MainPageDTO;
import com.test.pulse.service.mainpage.MainPageService;

import io.swagger.annotations.Api;

@Api(tags = "Main Page API")
@RestController
@RequestMapping("/api/main") // ✅ contextPath (/pulse)는 넣지 않는다!
public class MainPageRestController {

    private final MainPageService mainPageService;

    public MainPageRestController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping
    public ResponseEntity<MainPageDTO> getMainPageData(
            @RequestParam double lat,
            @RequestParam double lon) {

        MainPageDTO dto = mainPageService.getMainPageData(lat, lon);
        return ResponseEntity.ok(dto);
    }
}
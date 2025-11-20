package com.test.pulse.restcontroller.mainpage;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.pulse.model.mainpage.MainPageDTO;
import com.test.pulse.service.mainpage.MainPageService;

import io.swagger.annotations.Api;

/**
 * 메인 페이지 관련 RESTful API를 제공하는 컨트롤러
 */
@Api(tags = "Main Page API")
@RestController
@RequestMapping("/api/main")
public class MainPageRestController {

    private final MainPageService mainPageService;

    public MainPageRestController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    /**
     * 사용자의 현재 위치(위도, 경도)를 기반으로 날씨 정보와 추천 코스 데이터를 반환한다.
     * @param lat 위도
     * @param lon 경도
     * @return 날씨 정보와 추천 코스 데이터를 담은 MainPageDTO 객체
     */
    @ApiOperation(value = "메인 페이지 데이터 조회", notes = "사용자의 현재 위치(위도, 경도)를 기반으로 날씨 정보와 추천 코스 데이터를 반환한다.")
    @GetMapping
    public ResponseEntity<MainPageDTO> getMainPageData(
            @ApiParam(value = "위도 (Latitude)", required = true, example = "37.498095")
            @RequestParam double lat,
            @ApiParam(value = "경도 (Longitude)", required = true, example = "127.027610")
            @RequestParam double lon) {

        MainPageDTO dto = mainPageService.getMainPageData(lat, lon);
        return ResponseEntity.ok(dto);
    }
}
package com.test.pulse.api;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.pulse.service.api.OpenStreetMapAPIService;

/**
 * OpenStreetMap API를 테스트하는 클래스
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OpenStreetMapAPITests {
	@Autowired
    private OpenStreetMapAPIService mapApiService;
	
	/**
	 * OpenStreetMapAPIService의 coordToAddress 메서드를 테스트한다.
	 */
	@Test
    public void coordToAddress_API_호출_테스트() {
        // (준비) 강남역 부근 실제 좌표
        double lat = 37.5013068;
        double lon = 127.0396567;

        // (실행)
        // RestTemplate을 사용해 OpenStreetMapAPI를 호출
        String address = mapApiService.coordToAddress(lat, lon);

        // (검증)
        System.out.println("API 응답 주소: " + address);
        assertNotNull(address); // (null이 아니어야 함)
        assertNotEquals("주소 변환 실패", address); // (실패 문자열이 아니어야 함)
        assertNotEquals("주소 정보 없음", address); // (실패 문자열이 아니어야 함)
    }

}

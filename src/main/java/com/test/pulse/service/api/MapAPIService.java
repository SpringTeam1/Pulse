package com.test.pulse.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MapAPIService {
	private static final Logger log = LoggerFactory.getLogger(MapAPIService.class);
	private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String NAVER_CLIENT_ID;
    private final String NAVER_CLIENT_SECRET;
	@Autowired
	public MapAPIService(
			RestTemplate restTemplate,
			ObjectMapper objectMapper,
			@Value("${naver.api.clientId}") String naverClientId,
			@Value("${naver.api.clientSecret}") String naverClientSecret) {
		
		// 주입된 값이 null이거나, 대괄호[] 안에 공백이 있는지 확인
        log.info("===== MapAPIService 생성자 ======");
        log.info("주입된 Client ID: [" + naverClientId + "]");
        log.info("주입된 Client Secret: [" + naverClientSecret + "]");
        // ---------------------
		
		this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.NAVER_CLIENT_ID = naverClientId;
        this.NAVER_CLIENT_SECRET = naverClientSecret;
	}
	
	/**
	 * 위도, 경도를 주소로 변환하는 메서드
	 * @param lat 위도
	 * @param lon 경도
	 * @return 변환된 주소 문자열
	 */
	public String coordToAddress(double lat, double lon) {
		String url = String.format("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=%f,%f&output=json&orders=roadaddr,addr", lon, lat);
		
		//http header 생성(naver 전용 인증 헤더, 필수)
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-ncp-apigw-api-key-id", NAVER_CLIENT_ID);
		headers.add("x-ncp-apigw-api-key", NAVER_CLIENT_SECRET);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		log.info("네이버 API 요청 URL: {}", url);
		try {
			//ReverseGeocoding API 호출, RestTemplate을 사용해 GET 요청 전송
			ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			log.info("naver API 응답 상태: {}", resp.getStatusCode());
			
			//API 응답 결과인 json 문자열을 파싱
			JsonNode root = objectMapper.readTree(resp.getBody());
			JsonNode results = root.path("results");
			
			//주소 추출
			if (results.isArray() && results.size() > 0) {
				JsonNode region = results.get(0).path("region");
				JsonNode land = results.get(0).path("land");
				
				String siDo = region.path("area1").path("name").asText();
				String siGunGu = region.path("area2").path("name").asText();
				String eupMyeonDong = land.path("name").asText();
				String number1 = land.path("number1").asText();
				
				String address = String.format("%s %s %s %s", siDo, siGunGu, eupMyeonDong, number1);
				return address.replaceAll("\\s+", " ").trim();
			} else {
				log.warn("Naver API 응답에 results가 비어있습니다. (좌표: {},{})", lat, lon);
                return "주소 정보 없음";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("Naver API 호출 중 예외 발생: {}", e.getMessage());
			return "주소 변환 실패";
		}
		
		
	}
	
}

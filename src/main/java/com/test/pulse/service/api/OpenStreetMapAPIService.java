package com.test.pulse.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value; // (키가 필요 없으므로 삭제)
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * OpenStreetMap API 관련 서비스를 제공하는 클래스
 */
@Service
public class OpenStreetMapAPIService {
	private static final Logger log = LoggerFactory.getLogger(OpenStreetMapAPIService.class);
	
	private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    /**
     * OpenStreetMapAPIService 생성자
     * @param restTemplate RestTemplate 객체
     * @param objectMapper ObjectMapper 객체
     */
	@Autowired
	public OpenStreetMapAPIService(
			RestTemplate restTemplate,
			ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
	}
	
	/**
	 * 위도, 경도를 주소로 변환하는 메서드 (OpenStreetMap Nominatim 사용)
	 * @param lat 위도
	 * @param lon 경도
	 * @return 변환된 주소 문자열
	 */
	public String coordToAddress(double lat, double lon) {
	    
	    //Nominatim API URL (키 필요 없음)
		String url = String.format(
		    "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", 
		    lat, lon
		);
		
		// OSM 정책에 따른 User-Agent 헤더 추가
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "PulseProject (Academic Use)"); 
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		log.info("Nominatim API 요청 URL: {}", url);
		try {
			ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			log.info("Nominatim API 응답 상태: {}", resp.getStatusCode());
			
			//json을 jsonnode 객체로 변환
			JsonNode root = objectMapper.readTree(resp.getBody());
			
			//주소 파싱을 위한 헬퍼 메서드 호출
			return parseAddressFromJson(root, lat, lon);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Nominatim API 호출 중 예외 발생: {}", e.getMessage());
			return "주소 변환 실패";
		}
	}

	/**
	 * Nominatim API 응답(JsonNode)을 파싱하여 주소 문자열로 조합한다.
	 * @param root API 응답 JSON의 최상위 노드
	 * @param lat 요청했던 위도 (로그용)
	 * @param lon 요청했던 경도 (로그용)
	 * @return "시/도 시/군/구 동" 형식의 주소 문자열
	 */
	private String parseAddressFromJson(JsonNode root, double lat, double lon) {
		JsonNode addrNode = root.path("address");
		
		//구성요소 개별 추출
		if(addrNode.isObject() && addrNode.size() > 0) {
			// OSM은 키 이름이 다양하므로, .asText(대체값)을 연쇄적으로 사용
			String siDo = addrNode.path("city").asText(addrNode.path("state").asText());
			
//			String siGunGu = addressNode.path("city_district")
//					.asText(addressNode.path("county")
//					.asText(addressNode.path("borough").asText()));
			
			String siGunGu = addrNode.path("city_district")
					.asText(addrNode.path("county")
					.asText(addrNode.path("borough").asText()));
			
			String eupMyeonDong = addrNode.path("neighbourhood")
					.asText(addrNode.path("suburb")
					.asText(addrNode.path("village")
					.asText(addrNode.path("town").asText())));
			
			String address = String.format("%s %s %s", siDo, siGunGu, eupMyeonDong);
			
			address = address.replaceAll("\\s+", " ").trim();
			log.info("파싱된 주소(parseAddressFromJson): {}", address);
			
			return address;
		} else {
	        // 6. 'address' 객체가 비어있을 경우 (예외 처리)
	        log.warn("Nominatim API 응답에 'address' 객체가 비어있습니다. (좌표: {},{})", lat, lon);
	        
	        // (예비) 'address'가 없으면 'display_name'이라도 반환
	        String displayName = root.path("display_name").asText();
	        if (!displayName.isEmpty()) {
	            return displayName; // 예: "테헤란로28길, 역삼동, ..., 대한민국"
	        }
	        
	        return "주소 정보 없음";
	    }
		
		
	}
}
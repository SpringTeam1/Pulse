package com.test.pulse.service.mainpage;

import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.test.pulse.model.mainpage.MainPageDTO;

@Service
public class MainPageService {

    private final RestTemplate restTemplate = new RestTemplate();

    public MainPageDTO getMainPageData(double lat, double lon) {

        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        int[] grid = convertToGrid(lat, lon);
        int nx = grid[0];
        int ny = grid[1];

        Base base = computeVillageBase();
        Target target = computeTarget();

        String serviceKey = "1941134e80a2ae941f53026fdff47172806cd75258c0ecd1b2f2f5b8a4a21817";
        String url = String.format(
            "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst" +
            "?serviceKey=%s&pageNo=1&numOfRows=1000&dataType=XML&base_date=%s&base_time=%s&nx=%d&ny=%d",
            URLEncoder.encode(serviceKey, StandardCharsets.UTF_8),
            base.baseDate, base.baseTime, nx, ny
        );

        String xmlResponse;
        try {
            xmlResponse = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            System.err.println("❌ HTTP 오류: " + e.getStatusCode() + " / " + e.getResponseBodyAsString());
            return emptyDto();
        } catch (Exception e) {
            e.printStackTrace();
            return emptyDto();
        }

        MainPageDTO dto = new MainPageDTO();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlResponse)));
            doc.getDocumentElement().normalize();

            String resultCode = textOrNull(doc, "resultCode");
            if (resultCode != null && !"00".equals(resultCode)) {
                String resultMsg = textOrNull(doc, "resultMsg");
                System.err.println("⚠️ API 결과 코드: " + resultCode + " / " + resultMsg);
                return emptyDto();
            }

            // ✅ 현재 시점 데이터
            String tmpVal = getFcstValue(doc, "TMP", target.fcstDate, target.fcstTime);
            String rehVal = getFcstValue(doc, "REH", target.fcstDate, target.fcstTime);
            String popVal = getFcstValue(doc, "POP", target.fcstDate, target.fcstTime);
            String skyVal = getFcstValue(doc, "SKY", target.fcstDate, target.fcstTime);
            String ptyVal = getFcstValue(doc, "PTY", target.fcstDate, target.fcstTime);

            if (tmpVal == null) tmpVal = getAnyFcstValue(doc, "TMP");
            if (rehVal == null) rehVal = getAnyFcstValue(doc, "REH");
            if (popVal == null) popVal = getAnyFcstValue(doc, "POP");
            if (skyVal == null) skyVal = getAnyFcstValue(doc, "SKY");
            if (ptyVal == null) ptyVal = getAnyFcstValue(doc, "PTY");

            dto.setTemp(tmpVal != null ? Double.parseDouble(tmpVal) : 0.0);
            dto.setHumidity(rehVal != null ? Integer.parseInt(rehVal) : 0);
            dto.setRainChance(popVal != null ? Integer.parseInt(popVal) : 0);
            dto.setSkyCondition(parseSkyCode(skyVal));
            dto.setPrecipitationType(parsePtyCode(ptyVal));

        } catch (Exception e) {
            e.printStackTrace();
            return emptyDto();
        }

        return dto;
    }

    private String mostFrequent(List<String> list) {
        return list.stream()
                .reduce((a, b) -> Collections.frequency(list, a) > Collections.frequency(list, b) ? a : b)
                .orElse("4");
    }

    // 이하 기존 메서드 그대로 ↓↓↓
    private Base computeVillageBase() {
        LocalDateTime now = LocalDateTime.now().minusHours(1);
        int[] slots = {2, 5, 8, 11, 14, 17, 20, 23};
        int hour = now.getHour();
        int chosen = -1;
        for (int h : slots) if (h <= hour) chosen = h;
        LocalDate baseDate = now.toLocalDate();
        if (chosen == -1) { baseDate = baseDate.minusDays(1); chosen = 23; }
        String baseDateStr = baseDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTimeStr = String.format("%02d00", chosen);
        return new Base(baseDateStr, baseTimeStr);
    }

    private Target computeTarget() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate fcstDate = now.toLocalDate();
        String fcstDateStr = fcstDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String fcstTimeStr = String.format("%02d00", now.getHour());
        return new Target(fcstDateStr, fcstTimeStr);
    }

    private String getFcstValue(Document doc, String category, String fcstDate, String fcstTime) {
        NodeList items = doc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            String cat = text(item, "category");
            String date = text(item, "fcstDate");
            String time = text(item, "fcstTime");
            if (category.equals(cat) && fcstDate.equals(date) && fcstTime.equals(time)) {
                return text(item, "fcstValue");
            }
        }
        return null;
    }

    private String getAnyFcstValue(Document doc, String category) {
        NodeList items = doc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            String cat = text(item, "category");
            if (category.equals(cat)) return text(item, "fcstValue");
        }
        return null;
    }

    private String textOrNull(Document doc, String tag) {
        NodeList list = doc.getElementsByTagName(tag);
        if (list.getLength() == 0) return null;
        return list.item(0).getTextContent();
    }

    private String text(Element e, String tag) {
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }

    private String parseSkyCode(String code) {
        if (code == null) return "정보없음";
        switch (code) {
            case "1":
                return "맑음";
            case "3":
                return "구름많음";
            case "4":
                return "흐림";
            default:
                return "정보없음";
        }
    }


    private String parsePtyCode(String code) {
    	if (code == null) return "정보없음";
        switch (code) {
            case "0":
                return "비/눈 오지않음";
            case "1":
                return "비";
            case "2":
                return "비/눈";
            case "3":
                return "눈";
            case "5":
                return "빗방울";
            case "6":
                return "빗방울눈날림";
            case "7":
                return "눈날림";
            default:
                return "정보없음";
        }
    }

    private int[] convertToGrid(double lat, double lon) {
        double RE = 6371.00877, GRID = 5.0, SLAT1 = 30.0, SLAT2 = 60.0, OLON = 126.0, OLAT = 38.0;
        double XO = 43, YO = 136;
        double DEGRAD = Math.PI / 180.0;
        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD, slat2 = SLAT2 * DEGRAD, olon = OLON * DEGRAD, olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) /
                    Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(Math.PI * 0.25 + (lat) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        int x = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        int y = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        return new int[]{x, y};
    }

    private MainPageDTO emptyDto() {
        MainPageDTO dto = new MainPageDTO();
        dto.setTemp(0.0);
        dto.setHumidity(0);
        dto.setRainChance(0);
        dto.setSkyCondition("정보없음");
        dto.setPrecipitationType("정보없음");
        return dto;
    }

    private static class Base { final String baseDate; final String baseTime; Base(String d, String t){this.baseDate=d;this.baseTime=t;} }
    private static class Target { final String fcstDate; final String fcstTime; Target(String d, String t){this.fcstDate=d;this.fcstTime=t;} }
}

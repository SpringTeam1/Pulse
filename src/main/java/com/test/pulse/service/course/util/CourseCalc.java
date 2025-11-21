package com.test.pulse.service.course.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.test.pulse.model.course.CoordinateDTO;

/**
 * 코스 계산 관련 유틸리티 클래스
 */
@Component
public class CourseCalc {
	/**
	 * 코스의 총 거리를 계산하는 메서드(단위: m)
	 * @param coords 좌표 리스트
	 * @return 총 거리 (미터 단위)
	 */
	public double calculateTotalDistance(List<CoordinateDTO> coords) {
		double totalDistance = 0.0;
        // 0번부터 마지막-1번까지 순회
        for (int i = 0; i < coords.size() - 1; i++) {
            CoordinateDTO start = coords.get(i);
            CoordinateDTO end = coords.get(i + 1);
            // 두 점 사이의 거리를 계속 더함
            totalDistance += haversineDistance(start.getLat(), start.getLon(), end.getLat(), end.getLon());
        }
        return totalDistance;
	}
	
	/**
	 * 하버사인 공식을 이용해 두 좌표 간의 거리를 계산하는 메서드
	 * @param lat1 첫 번째 지점의 위도
	 * @param lon1 첫 번째 지점의 경도
	 * @param lat2 두 번째 지점의 위도
	 * @param lon2 두 번째 지점의 경도
	 * @return 두 지점 간의 거리 (미터 단위)
	 */
	public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // 지구 반지름 (km)
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c * 1000; // m 단위로 변환
	}
}

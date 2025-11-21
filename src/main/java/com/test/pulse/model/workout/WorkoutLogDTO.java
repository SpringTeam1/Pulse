package com.test.pulse.model.workout;

import java.time.LocalDate;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

/**
 * 운동 기록 목록(로그)에 표시될 요약 정보를 담는 데이터 전송 객체(DTO)
 * 전체 운동 기록 중 일부 핵심 정보만을 포함한다.
 */
@Getter @Setter @ToString
public class WorkoutLogDTO {

	/**
     * 운동 기록의 고유 번호 (Primary Key)
     */
	private int workoutSeq;
	
	/**
     * 운동을 수행한 사용자의 계정 ID
     */
	private String accountId;
	
	/**
     * 총 운동 시간 (초 또는 밀리초 단위)
     */
	private long totalTime;
	
	/**
     * 총 이동 거리 (단위: km)
     */
	private double totalDistance;
	
	/**
     * 총 소모 칼로리
     */
	private int totalCalories;
	
	/**
     * 평균 페이스 (km당 시간)
     */
	private double avgPace;
	
	/**
     * 운동을 수행한 날짜
     */
	private String workoutDate;
	
}

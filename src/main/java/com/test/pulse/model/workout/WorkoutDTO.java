package com.test.pulse.model.workout;

import java.time.LocalDate;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

/**
 * 사용자의 단일 운동 세션에 대한 모든 데이터를 담는 데이터 전송 객체(DTO)
 * 운동 경로, 통계, 사용자 메모 등의 정보를 포함한다.
 */
@Getter @Setter @ToString
public class WorkoutDTO {

	/**
     * 운동 기록의 고유 번호 (Primary Key)
     */
	private int workoutSeq;
	
	/**
     * 운동을 수행한 사용자의 계정 ID
     */
	private String accountId;
	
	/**
     * 운동 경로의 좌표 데이터를 담은 JSON 형태의 문자열
     */
	private String trackData;
	
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
	
	/**
     * 운동 기록에 첨부된 이미지 또는 파일의 이름
     */
	private String attachment;
	
	/**
     * 운동에 대한 사용자의 메모 또는 코멘트
     */
	private String exerciseComment;
	
}

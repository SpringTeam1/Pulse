package com.test.pulse.model.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 코스 목록 페이지에서 개별 코스를 카드 형태로 보여주기 위해 필요한 요약 정보를 담는 데이터 전송 객체(DTO)
 */
@Getter @Setter @ToString
public class CourseCardDTO {
	/**
     * 코스의 고유 번호 (Primary Key)
     */
	private int courseSeq;
	
	/**
     * 코스의 이름
     */
	private String courseName;
	
	/**
     * 코스의 총 길이 (단위: km)
     */
	private double courseLength;
	
	/**
     * 코스 시작 지점의 주소
     */
	private String startAddress;
	
	/**
     * 코스 종료 지점의 주소
     */
	private String endAddress;
	
	/**
     * 코스가 '즐겨찾기'된 횟수
     */
	private int favoriteCount;
	
	/**
     * 코스를 등록한 사용자의 계정 ID
     */
	private String accountId;
}

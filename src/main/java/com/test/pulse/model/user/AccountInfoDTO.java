package com.test.pulse.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 계정 정보를 담는 데이터 전송 객체(DTO)
 * 기본 로그인 정보(`tblAccountInfo`)와 개인 상세 정보(`tblAccountDetail`)를 모두 포함한다.
 */
@Getter
@Setter
@ToString
public class AccountInfoDTO {
	
	// tblAccountInfo (기본 정보)
	/**
     * 사용자 계정 ID (Primary Key)
     */
	private String accountId;
	/**
     * 사용자 비밀번호 (암호화된 상태)
     */
	private String password;
	/**
     * 프로필 사진 파일명
     */
	private String profilePhoto;
	/**
     * 사용자 닉네임
     */
	private String nickname;
	/**
     * 계정 권한 (예: ROLE_USER, ROLE_ADMIN)
     */
	private String accountRole;
	/**
     * 계정 고유 번호
     */
	private String accountSeq;
	/**
     * 사용자 레벨
     */
	private String accountLevel;
	/**
     * 가입 유형 (예: normal, kakao)
     */
	private String registerType;
	/**
     * 사용자 분류
     */
	private String accountCategory;
	
	// tblAccountDetail (상세 정보)
	/**
     * 사용자 상세 정보 테이블의 고유 번호 (Primary Key)
     */
	private String accountInfoDetailSeq;
	/**
     * 사용자 실명
     */
	private String name;
	/**
     * 전화번호
     */
	private String phoneNum;
	/**
     * 생년월일
     */
	private String birthday;
	/**
     * 성별
     */
	private String gender;
	/**
     * 거주 지역 (시/도)
     */
	private String regionCity;
	/**
     * 거주 지역 (시/군/구)
     */
	private String regionCounty;
	/**
     * 거주 지역 (읍/면/동)
     */
	private String regionDistrict;
	/**
     * 주간 운동 횟수
     */
	private String exerciseFrequency;
	/**
     * 가입일
     */
	private String joinDate;

}

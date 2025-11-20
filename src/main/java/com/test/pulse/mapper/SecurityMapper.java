package com.test.pulse.mapper;

import com.test.pulse.model.user.AccountInfoDTO;

/**
 * 사용자 계정 생성 등 보안 관련 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
public interface SecurityMapper {
	
	/**
	 * 데이터베이스 서버의 현재 시간을 조회한다.
	 * 데이터베이스 연결 테스트를 위해 사용된다.
	 *
	 * @return 데이터베이스 서버의 현재 시간 문자열
	 */
	String time();

	/**
     * 새로운 사용자 계정을 데이터베이스에 등록한다.
     *
     * @param dto 등록할 사용자의 계정 정보를 담은 DTO
     */
	void add(AccountInfoDTO dto);

}




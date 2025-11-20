package com.test.pulse.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 데이터베이스 연결 및 MyBatis 매퍼 기능 테스트를 위한 인터페이스 매퍼
 */
@Mapper
public interface TestMapper {

	/**
	 * 테스트용 문자열을 데이터베이스에서 조회한다.
	 * DB 연결 상태와 매퍼의 정상 동작을 확인하기 위해 사용된다.
	 *
	 * @return 데이터베이스에서 조회한 테스트 문자열
	 */
	public String getTest();
	
}

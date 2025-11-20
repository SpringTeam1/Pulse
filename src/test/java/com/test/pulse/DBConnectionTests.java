package com.test.pulse;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.pulse.mapper.TestMapper;


/**
 * 데이터베이스 연결을 테스트하는 클래스
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DBConnectionTests {


	@Autowired
	private TestMapper testMapper;
	/**
	 * TestMapper를 통해 데이터를 조회하여 데이터베이스 연결을 테스트합니다.
	 */
	@Test
	public void test() {
		
		assertNotNull(testMapper);
		
		String test1 = testMapper.getTest();
		
		System.out.println(test1);
		
	}

	

}

package com.test.pulse;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.pulse.mapper.TestMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DBConnectionTests {


	@Autowired
	private TestMapper testMapper;
	@Test
	public void test() {
		
		assertNotNull(testMapper);
		
		String test1 = testMapper.getTest();
		
		System.out.println(test1);
		
	}

	

}

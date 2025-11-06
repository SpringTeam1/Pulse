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
	private TestMapper test;
	
	@Test
	public void test() {
		
		assertNotNull(test);
		
		String test1 = test.getTest();
		
		System.out.println(test1);
		
	}

	

}

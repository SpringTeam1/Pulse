package com.test.pulse.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.pulse.model.SecurityMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
public class AddAccountInfo {
	
	@Autowired
	private SecurityMapper mapper;
	
	@Test
	public void add() {
		
		assertNotNull(mapper);
		
		AccountInfoDTO dto = new AccountInfoDTO();
		
		dto.setId("hong");
		dto.setPw("1111");
		
		mapper.add(dto);
		
	}

}

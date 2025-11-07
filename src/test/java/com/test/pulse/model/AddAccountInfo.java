package com.test.pulse.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.pulse.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
public class AddAccountInfo {

	@Autowired
	private UserMapper mapper;
	
	@Test
	public void add() {
		
		assertNotNull(mapper);
		
		AccountInfoDTO dto = new AccountInfoDTO();
		
		dto.setAccountId("hong");
		dto.setPassword("1111");
		dto.setProfilePhoto("default");
		dto.setNickname("홍길동");
		dto.setAccountRole("일반");
		dto.setAccountSeq("1");
		dto.setAccountLevel("1");
		dto.setRegisterType("기본");
		dto.setAccountCategory("활성");
		
		mapper.add(dto);
		
	}

}

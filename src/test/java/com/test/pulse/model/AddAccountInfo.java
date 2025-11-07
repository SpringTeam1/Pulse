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
		
		dto.setAccountId("test3");
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
	
	@Test
    public void addDetail() {

        assertNotNull(mapper);

        AccountInfoDTO dto = new AccountInfoDTO();

        dto.setAccountInfoDetailSeq("5");
        dto.setAccountId("hong");
        dto.setName("강아지");
        dto.setPhoneNum("010-1111-1111");
        dto.setBirthday("2000-10-22");
        dto.setGender("1");
        dto.setRegionCity("서울특별시");
        dto.setRegionCounty("강남구");
        dto.setRegionDistrict("서초동");
        dto.setExerciseFrequency("1");
        dto.setJoinDate("2025-02-22");

        mapper.addDetail(dto);

    }
	

}

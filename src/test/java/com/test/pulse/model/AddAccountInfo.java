package com.test.pulse.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;

/**
 * 사용자 계정 정보에 대한 테스트 클래스
 */
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
	
	/**
	 * 새로운 사용자 계정 추가를 테스트한다.
	 */
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
	
	/**
	 * 사용자 상세 정보 추가를 테스트한다.
	 */
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
	
	/**
	 * 사용자 계정 정보 수정을 테스트한다.
	 */
	@Test
	public void update() {
		
		assertNotNull(mapper);
		
		AccountInfoDTO dto = new AccountInfoDTO();
		
		dto.setAccountId("zkfps411@naver.com");
		dto.setPassword("$2a$10$D6zR5o.uIy/peg2ih2VQY.BDVKOQdQsGigvVcELQ34MK0mPPhztcK");
		dto.setProfilePhoto("pic.png");
		dto.setNickname("홍길동");
		
		mapper.update(dto);
		
	}
	
	/**
	 * 사용자 상세 정보 수정을 테스트한다.
	 */
	@Test
    public void updateDetail() {

        assertNotNull(mapper);

        AccountInfoDTO dto = new AccountInfoDTO();
        
        dto.setAccountId("zkfps411@naver.com");
        dto.setName("강아지");
        dto.setPhoneNum("010-2222-2222");
        dto.setBirthday("2000-10-22");
        dto.setGender("여자");
        dto.setRegionCity("서울특별시");
        dto.setRegionCounty("강서구");
        dto.setRegionDistrict("신라동");
        dto.setExerciseFrequency("4-6");

        mapper.updateDetail(dto);

    }
	
	/**
	 * 사용자 계정 삭제를 테스트한다.
	 */
	@Test
	public void deleteaccount() {
		
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
	
	/**
	 * 사용자 상세 정보 삭제를 테스트한다.
	 */
	@Test
    public void deleteaccountDetail() {

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

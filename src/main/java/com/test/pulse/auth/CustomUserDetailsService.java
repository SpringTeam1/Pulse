package com.test.pulse.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;
import com.test.pulse.model.user.CustomUser;

/**
 * Spring Security에서 사용자 정보를 로드하는 UserDetailsService 구현 클래스
 */
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper mapper;
	
	/**
	 * 사용자 아이디를 기반으로 사용자 정보를 로드한다.
	 * 로그인 시 Spring Security에 의해 호출
	 *
	 * @param accountId 사용자 아이디
	 * @return UserDetails 객체
	 * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때 발생하는 예외
	 */
	@Override
	public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
		
		AccountInfoDTO dto = mapper.get(accountId);
		
		if (dto == null) {
		    throw new UsernameNotFoundException("User not found: " + accountId);
		}
		
		AccountInfoDTO detail = mapper.getDetail(accountId);

	    if (detail != null) {
	        dto.setName(detail.getName());
	        dto.setPhoneNum(detail.getPhoneNum());
	        dto.setBirthday(detail.getBirthday());
	        dto.setGender(detail.getGender());
	        dto.setRegionCity(detail.getRegionCity());
	        dto.setRegionCounty(detail.getRegionCounty());
	        dto.setRegionDistrict(detail.getRegionDistrict());
	        dto.setExerciseFrequency(detail.getExerciseFrequency());
	    }
		
		return new CustomUser(dto);
	}

}
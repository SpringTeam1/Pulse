package com.test.pulse.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;
import com.test.pulse.model.user.CustomUser;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper mapper;
	
	//로그인 발생 시 같이 호출
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AccountInfoDTO dto = mapper.get(username);
		
		return dto != null ? new CustomUser(dto) : null;
	}

}

package com.test.pulse.model.user;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

//회원 정보 객체
//- 기존의 User 대신
@Getter
public class CustomUser extends User {
	
	private AccountInfoDTO adto;
	
	public CustomUser(String username, String password,
						Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);		
	}
	
	public CustomUser(AccountInfoDTO dto) {
		
		//List<AuthDTO> > (매핑) > List<SimpleGrantedAuthority>
		
		super(dto.getAccountId(), dto.getPassword()
				, java.util.List.of(new SimpleGrantedAuthority(dto.getAccountRole())));
		
		this.adto = dto;
		
	}	

}







package com.test.pulse.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

/**
 * Spring Security의 'User' 클래스를 확장하여 애플리케이션의 사용자 정보를 포함하는 커스텀 클래스
 * 인증이 완료되면, 이 객체는 SecurityContext에 'Principal'로 저장되어 애플리케이션 전반에서 사용자 정보에 접근하는 데 사용한다.
 */
@Getter
public class CustomUser extends User {
	
	/**
     * 로그인한 사용자의 모든 계정 정보(기본 및 상세)를 담고 있는 DTO
     */
	private AccountInfoDTO adto;
	
	/**
     * Spring Security의 'User' 클래스와 호환성을 위한 기본 생성자
     *
     * @param username    사용자 ID
     * @param password    사용자 비밀번호
     * @param authorities 사용자가 가진 권한 목록
     */
	public CustomUser(String username, String password,
						Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);		
	}
	
	/**
     * AccountInfoDTO를 사용하여 CustomUser 객체를 생성한다.
     * 사용자 ID, 비밀번호, 그리고 역할(Role)에 따른 권한을 설정한다.
     *
     * @param dto 로그인한 사용자의 정보를 담은 AccountInfoDTO
     */
	public CustomUser(AccountInfoDTO dto) {
		
		super(dto.getAccountId(), dto.getPassword(), getAuthorities(dto.getAccountRole()));
		this.adto = dto;
		
	}
	
	/**
     * 문자열 형태의 역할을 Spring Security가 이해할 수 있는 'GrantedAuthority' 컬렉션으로 변환하는 정적 헬퍼 메소드
     *
     * @param role 데이터베이스에서 조회한 사용자 역할 문자열 (예: "관리자", "일반")
     * @return Spring Security 권한 객체(SimpleGrantedAuthority)의 리스트
     */
	private static Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("관리자".equals(role)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
		// 모든 사용자는 기본적으로 ROLE_MEMBER 권한을 가진다.
        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        return authorities;
    }

}

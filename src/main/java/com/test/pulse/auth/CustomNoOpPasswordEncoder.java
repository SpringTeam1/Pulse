package com.test.pulse.auth;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security에서 비밀번호를 암호화하지 않고 평문으로 처리하는 PasswordEncoder 구현 클래스
 * 테스트 또는 개발 목적으로 사용 가능
 */
public class CustomNoOpPasswordEncoder implements PasswordEncoder {
	
	/**
	 * 주어진 비밀번호를 인코딩한다. 이 구현에서는 아무 작업도 수행하지 않고 원본 비밀번호를 반환한다.
	 *
	 * @param rawPassword 인코딩할 비밀번호
	 * @return 인코딩된 비밀번호 (이 경우 원본 비밀번호)
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		//사용자가 입력한 암호 > (암호화) > 반환
		System.out.println("원래 암호: " + rawPassword);		 
		return rawPassword.toString();
	}

	/**
	 * 입력된 비밀번호와 인코딩된 비밀번호가 일치하는지 확인
	 *
	 * @param rawPassword     사용자가 입력한 비밀번호
	 * @param encodedPassword 저장된 인코딩된 비밀번호
	 * @return 비밀번호가 일치하면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {		
		//사용자가 입력한 암호 vs 인코딩된 암호		
		System.out.println("암호 비교: " + rawPassword + "," + encodedPassword);
		return rawPassword.toString().equals(encodedPassword);
	}

}
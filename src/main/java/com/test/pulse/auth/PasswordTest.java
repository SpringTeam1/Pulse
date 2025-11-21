package com.test.pulse.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCryptPasswordEncoder를 사용하여 비밀번호를 인코딩하는 테스트 클래스
 */
public class PasswordTest {

	/**
	 * 관리자 비밀번호를 BCrypt로 인코딩하여 콘솔에 출력한다.
	 *
	 * @param args 커맨드 라인 인수 (사용하지 않음)
	 */
	public static void main(String[] args) {
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String raw = "1q2w3e4r"; // 사용할 관리자 비밀번호
		String encoded = encoder.encode(raw);
		System.out.println(encoded);
	    
	}


}
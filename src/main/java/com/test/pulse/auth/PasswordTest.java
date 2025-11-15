package com.test.pulse.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

	public static void main(String[] args) {
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String raw = "1q2w3e4r"; // 사용할 관리자 비밀번호
		String encoded = encoder.encode(raw);
		System.out.println(encoded);
	    
	}


}

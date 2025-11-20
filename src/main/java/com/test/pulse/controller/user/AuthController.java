package com.test.pulse.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 인증 관련 요청을 처리하는 컨트롤러 클래스 (로그인, 로그아웃, 접근 거부 등)
 */
@Controller
public class AuthController {

	/**
	 * 접근 거부 페이지를 반환한다.
	 *
	 * @return 접근 거부 페이지 뷰 이름
	 */
	@GetMapping("/accesserror")
	public String accesserror() {
		
		return "user.accesserror";
	}
	
	/**
	 * 커스텀 로그인 페이지를 반환한다.
	 *
	 * @return 커스텀 로그인 페이지 뷰 이름
	 */
	@GetMapping("/customlogin")
	public String customlogin() {
		
		return "user.customlogin";
	}
	
	/**
	 * 커스텀 로그아웃을 처리한다.
	 *
	 * @return 커스텀 로그아웃 페이지 뷰 이름
	 */
	@PostMapping("/customlogout")
	public String customlogout() {
		
		return "user.customlogout";
	}
	
	/**
	 * 메인 인덱스 페이지를 반환한다.
	 *
	 * @return 인덱스 페이지 뷰 이름
	 */
	@GetMapping("/index")
	public String index() {
		
		return "index";
	}
	
}
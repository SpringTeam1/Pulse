package com.test.pulse.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Spring Security에서 접근 거부(403) 오류가 발생했을 때 처리를 담당하는 핸들러 클래스
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	/**
	 * 접근이 거부되었을 때 호출되는 메소드
	 * 사용자를 접근 거부 오류 페이지("/pulse/accesserror")로 리디렉션한다.
	 *
	 * @param request HttpServletRequest 객체
	 * @param response HttpServletResponse 객체
	 * @param accessDeniedException 발생한 AccessDeniedException 객체
	 * @throws IOException 입출력 예외 발생 시
	 * @throws ServletException 서블릿 예외 발생 시
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		System.out.println("403 오류 관련 처리 진행..");
		
		response.sendRedirect("/pulse/accesserror");
	}

}
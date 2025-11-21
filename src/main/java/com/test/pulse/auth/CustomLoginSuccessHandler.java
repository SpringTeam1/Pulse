package com.test.pulse.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

/**
 * Spring Security에서 로그인 성공 후 처리를 담당하는 핸들러 클래스
 */
public class CustomLoginSuccessHandler  implements AuthenticationSuccessHandler {
	
	//로그인 직전 접속했던 기록 제공
	private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
	

	/**
	 * 로그인 성공 시 호출되는 메소드
	 * 사용자의 권한에 따라 다른 페이지로 리디렉션
	 * 로그인 전 요청했던 URL이 있는 경우 해당 URL로 이동
	 *
	 * @param request        HttpServletRequest 객체
	 * @param response       HttpServletResponse 객체
	 * @param authentication Authentication 객체
	 * @throws IOException      입출력 예외 발생 시
	 * @throws ServletException 서블릿 예외 발생 시
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println("CustomLoginSuccessHandler 호출");
		
		List<String> roleNames = new ArrayList<String>();
		
		authentication.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		//3. 로그인 전 요청했던 URL 이동하기
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if (savedRequest != null) {
			//로그인 전 URL 존재
			response.sendRedirect(savedRequest.getRedirectUrl());
			return;
		} else if (roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		} else if (roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect(request.getContextPath() + "/index.do");
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/accesserror");
			return;
		}
		
	}

}
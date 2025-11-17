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

public class CustomLoginSuccessHandler  implements AuthenticationSuccessHandler {
	
	//로그인 직전 접속했던 기록 제공
	private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
	

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

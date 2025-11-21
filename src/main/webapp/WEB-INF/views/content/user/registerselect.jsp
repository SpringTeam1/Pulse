<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!--  -->
	<h2>회원가입</h2>
	
	<!-- 일반 회원가입 -->
	<form method="GET" action="/pulse/register">
	<input type="hidden" name="registerType" value="기본">
	<table class="vertical">
		<tr>
			<th>일반 회원가입</th>
			<td>
        		<button type="submit" class="title" style="all:unset;cursor:pointer;">Pulse</button>
      		</td>
		</tr>
	</table>
	</form>
	
	
	<!-- 소셜 로그인 -->
	<table class="vertical">
		<tr>
			<th>소셜 로그인</th>
	
			<td><div id="g_id_onload"
			     data-client_id="757281038435-f89f408pe749euhcgbrct110vfjgp2au.apps.googleusercontent.com"
			     data-context="signin"
			     data-ux_mode="redirect"
			     data-login_uri="http://localhost:8080/user/googleoauthcallback.do"
			     data-auto_prompt="false">
			</div></td>
	
			<td><div class="g_id_signin"
			     data-type="icon"
			     data-shape="square"
			     data-theme="outline"
			     data-text="signup_with"
			     data-size="large"
			     data-locale="ko"
			     style="display: flex; justify-content: center;">
			</div></td>
		</tr>
	</table>

</body>
</html>



















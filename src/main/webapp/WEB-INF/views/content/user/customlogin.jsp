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
	<!-- customlogin.jsp -->
	
	<h2>로그인</h2>
	
	<form method="POST" action="/pulse/customlogin.do">
	<table class="vertical content">
		<tr>
			<th>아이디</th>
			<td><input type="text" name="accountId" required></td>
		</tr>
		<tr>
			<th>암호</th>
			<td><input type="password" name="password" required></td>
		</tr>
	</table>
	<div>
		<button class="in">로그인</button>
	</div>
	
	<!-- 
		CSRF 토큰 : 지금 POST(PUT,PATCH,DELETE) 요청은 내가 직접 하는 것이다!!
		
		<input type="hidden" name="_csrf" value="824c1ab1-98fb-4210-9648-a941f288d0e3">	 
	-->
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">	
	</form>
	
</body>
</html>















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
	<!-- customlogout.jsp -->
	
	<h2>로그아웃</h2>
	
	<form method="GET" action="/customlogout">
	<div>
		<button class="out">로그아웃</button>
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">	
	</form>
	
</body>
</html>















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
	<!-- register.jsp -->
	
	<h2>Register Page</h2>
	
	<form method="POST" action="/pulse/registerok.do">
	<table class="vertical">
		<tr>
			<th>아이디</th>
			<td><input type="text" name="accountId" required></td>
		</tr>
		<tr>
			<th>암호</th>
			<td><input type="password" name="password" required></td>
		</tr>
		<tr>
			<th>닉네임</th>
			<td><input type="text" name="nickname" required></td>
		</tr>
		<tr>
			<th>회원가입유형</th>
			<td><input type="text" name="registerType" required></td>
		</tr>
		<tr>
			<th>권한</th>
			<td>
				<select name="auth">
					<option value="일반">일반회원(ROLE_MEMBER)</option>
					<option value="관리자">관리자(ROLE_AMIN)</option>
				</select>
			</td>
		</tr>
	</table>
	<div>
		<button>가입하기</button>
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	
</body>
</html>















<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.button-link {
    display: inline-block;
    padding: 8px 16px;
    background-color: #007BFF;
    color: white;
    text-decoration: none;
    border-radius: 4px;
    cursor: pointer;
}
.button-link:hover {
    background-color: #0056b3;
}
</style>
</head>
<body>

	<!--  -->
	<h2>회원 탈퇴</h2>
	
	<form action="${pageContext.request.contextPath}/deleteaccountok" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	    <button type="submit">회원 탈퇴</button>
	</form>
	

</body>
</html>

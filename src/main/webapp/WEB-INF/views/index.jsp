<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
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
	<!-- index.jsp -->
	
	<h2>Index Page</h2>
	
	<p>모든 사용자가 접근 가능합니다.</p>
	
	<sec:authorize access="isAuthenticated()">
	    <p><sec:authentication property="principal.adto.nickname" />님 어서오세요</p>
		<form method="POST" action="<c:url value='/customlogout' />" style="display:inline;">
		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		    <button type="submit" class="button-link">로그아웃</button>
		</form>
	</sec:authorize>
	
	<sec:authorize access="!isAuthenticated()">
	    <a href="<c:url value='/customlogin' />">로그인</a>
	    <a href="<c:url value='/register' />">회원가입</a>
	</sec:authorize>


	
	
	
	
</body>
</html>















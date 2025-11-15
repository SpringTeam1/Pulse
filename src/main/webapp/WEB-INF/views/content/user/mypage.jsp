<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!--  -->
	<h2>마이페이지</h2>
	
	<sec:authorize access="isAuthenticated()">
    	<sec:authentication property="principal.adto.profilePhoto" var="profilePhoto"/>
    	<sec:authentication property="principal.adto.nickname" var="nickname"/>

	    <p>
	        <img src="${pageContext.request.contextPath}/asset/pic/${profilePhoto}" alt="Profile Photo" style="width:50px; height:50px;">
	        ${nickname}님
	    </p>
	    <hr>
	
	    <form method="GET" action="<c:url value='/infoedit' />">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	        <button type="submit" class="button-link">내 정보 수정</button>
	    </form>
	    <form method="GET" action="<c:url value='/myactivities' />">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	        <button type="submit" class="button-link">내 활동 기록</button>
	    </form>
	    <form method="GET" action="<c:url value='/deleteaccount' />">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	        <button type="submit" class="button-link">회원 탈퇴</button>
	    </form>
	</sec:authorize>

</body>
</html>

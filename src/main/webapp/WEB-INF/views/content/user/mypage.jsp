<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 text-gray-800">

<section class="max-w-4xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">
  <h2 class="text-3xl font-bold text-gray-900">마이페이지</h2>

  <sec:authorize access="isAuthenticated()">
    <c:set var="profilePhoto" value="${adto.profilePhoto}" />
    <sec:authentication property="principal.adto.nickname" var="nickname"/>

    <div class="flex items-center space-x-4">
      <img src="${pageContext.request.contextPath}/asset/pic/${profilePhoto}?v=${now}" alt="Profile Photo" class="w-16 h-16 rounded-full object-cover border border-gray-300" />
      <p class="text-xl font-medium">${adto.nickname}님</p>
    </div>

    <hr class="my-6 border-t border-gray-200">

    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <form method="GET" action="<c:url value='/infoedit' />">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit" class="w-full px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition">내 정보 수정</button>
      </form>

      <form method="GET" action="<c:url value='/myactivities' />">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit" class="w-full px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition">내 활동 기록</button>
      </form>

      <form method="GET" action="<c:url value='/deleteaccount' />">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit" class="w-full px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition">회원 탈퇴</button>
      </form>
    </div>
  </sec:authorize>
</section>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-5xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원 탈퇴</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 text-gray-800">

    <div class="text-center">
      <h2 class="text-2xl font-bold text-red-600">회원 탈퇴</h2>
      <p class="mt-2 text-gray-600">정말로 회원 탈퇴를 진행하시겠습니까?</p>
    </div>

    <form action="${pageContext.request.contextPath}/deleteaccountok" method="post" class="text-center">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      <button type="submit" class="px-6 py-2 bg-red-600 hover:bg-red-700 text-white font-semibold rounded-md">
        회원 탈퇴
      </button>
    </form>

  </section>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

<section class="max-w-3xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">
	<h2 class="text-2xl font-semibold text-gray-800">회원가입 성공</h2>
	<p class="text-gray-600">이제 로그인하여 서비스를 이용하실 수 있습니다.</p>
	<a href="<c:url value='/customlogin' />" class="inline-block px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">로그인</a>
</section>

</body>
</html>

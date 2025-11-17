<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<section class="max-w-md mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인</title>
	<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

	<h2 class="text-2xl font-bold text-gray-800 text-center mb-6">로그인</h2>

	<form method="POST" action="${pageContext.request.contextPath}/customlogin" class="space-y-6">
		<div class="space-y-4">
			<div>
				<label for="accountId" class="block text-gray-700 font-medium mb-1">아이디</label>
				<input type="text" id="accountId" name="accountId" required
					class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
			</div>

			<div>
				<label for="password" class="block text-gray-700 font-medium mb-1">암호</label>
				<input type="password" id="password" name="password" required
					class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
			</div>
		</div>

		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

		<div class="text-center">
			<button type="submit"
				class="bg-blue-600 text-white px-6 py-2 rounded-md hover:bg-blue-700 transition-colors">
				로그인
			</button>
		</div>
	</form>

</section>
</body>
</html>

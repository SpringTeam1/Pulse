<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<title>회원 정보 수정</title>
	<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 py-10">
	
	<section class="max-w-5xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">
		<h2 class="text-2xl font-bold">회원 정보 수정</h2>

		<sec:authorize access="isAuthenticated()">
			<c:set var="user" value="${adto}" />
			<c:set var="birthYear" value="${fn:substring(user.birthday, 0, 4)}" />
			<c:set var="birthMonth" value="${fn:substring(user.birthday, 5, 7)}" />
			<c:set var="birthDay" value="${fn:substring(user.birthday, 8, 10)}" />
		
			<form method="POST" action="/pulse/infoedit.do" enctype="multipart/form-data" class="space-y-6">
				
				<!-- 프로필 사진 -->
				<div>
					<label class="block font-semibold mb-2">프로필 사진</label>
					<div class="relative w-56 aspect-square mb-3">
						<img id="preview" src="${pageContext.request.contextPath}/asset/pic/${user.profilePhoto}?v=${now}" alt="Profile Photo" class="w-full h-full object-cover rounded shadow cursor-pointer">
						<label class="absolute inset-0 cursor-pointer" for="photo" title="이미지 선택"></label>
					</div>
					<input type="file" id="photo" name="photoFile" accept="image/png,image/jpeg" class="hidden">
					<input type="hidden" id="photoName" name="profilePhoto" value="${user.profilePhoto}">
					<div class="space-x-2">
						<button type="button" class="px-4 py-2 bg-blue-600 text-white rounded" onclick="photo.click()">이미지 선택</button>
						<button type="button" class="px-4 py-2 bg-gray-300 rounded" onclick="resetPhoto()">이미지 삭제</button>
					</div>
				</div>

				<!-- 이름 -->
				<div>
					<label class="block font-semibold mb-1" for="name">이름</label>
					<input type="text" id="name" name="name" value="${user.name}" required class="w-full border rounded px-3 py-2">
				</div>

				<!-- 닉네임 -->
				<div>
					<label class="block font-semibold mb-1" for="nickname">닉네임</label>
					<input type="text" id="nickname" name="nickname" value="${user.nickname}" required class="w-full border rounded px-3 py-2">
				</div>

				<!-- 전화번호 -->
				<div>
					<label class="block font-semibold mb-1">전화번호</label>
					<input type="text" name="phoneNum" value="${user.phoneNum}" required class="w-full border rounded px-3 py-2">
					<p class="text-sm text-gray-500 mt-1">'XXX-XXXX-XXXX'형식으로 입력해주세요.</p>
				</div>

				<!-- 생년월일 -->
				<div>
					<label class="block font-semibold mb-2">생년월일</label>
					<div class="grid grid-cols-3 gap-4">
						<div>
							<label class="block text-sm mb-1">연도</label>
							<select id="yyyy" name="yyyy" class="w-full border rounded px-3 py-2"></select>
						</div>
						<div>
							<label class="block text-sm mb-1">월</label>
							<select id="mm" name="mm" class="w-full border rounded px-3 py-2"></select>
						</div>
						<div>
							<label class="block text-sm mb-1">일</label>
							<select id="dd" name="dd" class="w-full border rounded px-3 py-2"></select>
						</div>
					</div>
					<input type="hidden" id="hiddenYear" value="${birthYear}" />
					<input type="hidden" id="hiddenMonth" value="${birthMonth}" />
					<input type="hidden" id="hiddenDay" value="${birthDay}" />
					<input type="hidden" id="birthday" name="birthday" value="${user.birthday}" required>
				</div>

				<!-- 성별 -->
				<div>
					<label class="block font-semibold mb-1">성별</label>
					<div class="flex gap-4">
						<label class="flex items-center gap-2"><input type="radio" name="gender" value="남자" <c:if test="${user.gender == '남자'}">checked</c:if>>남자</label>
						<label class="flex items-center gap-2"><input type="radio" name="gender" value="여자" <c:if test="${user.gender == '여자'}">checked</c:if>>여자</label>
					</div>
				</div>

				<!-- 지역 정보 -->
				<div>
					<label class="block font-semibold mb-1">광역시</label>
					<select id="sido" name="regionCity" class="w-full border rounded px-3 py-2">
						<option value="">선택</option>
						<option value="서울특별시" <c:if test="${user.regionCity == '서울특별시'}">selected</c:if>>서울특별시</option>
						<!-- 나머지 옵션 생략 -->
					</select>
					<c:if test="${not empty regionCityMsg}">
						<p class="text-sm text-red-500">${regionCityMsg}</p>
					</c:if>
				</div>

				<div>
					<label class="block font-semibold mb-1">시군구</label>
					<input type="text" id="sigungu" name="regionCounty" value="${user.regionCounty}" class="w-full border rounded px-3 py-2">
					<c:if test="${not empty regionCountyMsg}">
						<p class="text-sm text-red-500">${regionCountyMsg}</p>
					</c:if>
				</div>

				<div>
					<label class="block font-semibold mb-1">동읍면</label>
					<input type="text" id="dong" name="regionDistrict" value="${user.regionDistrict}" class="w-full border rounded px-3 py-2">
					<c:if test="${not empty regionDistrictMsg}">
						<p class="text-sm text-red-500">${regionDistrictMsg}</p>
					</c:if>
				</div>

				<!-- 운동빈도 -->
				<div>
					<label class="block font-semibold mb-1">운동 빈도</label>
					<div class="flex flex-wrap gap-4">
						<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="0" <c:if test="${user.exerciseFrequency == '0'}">checked</c:if>>주 1회 미만</label>
						<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="1-3" <c:if test="${user.exerciseFrequency == '1-3'}">checked</c:if>>주 1~3회</label>
						<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="4-6" <c:if test="${user.exerciseFrequency == '4-6'}">checked</c:if>>주 4~6회</label>
						<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="7" <c:if test="${user.exerciseFrequency == '7'}">checked</c:if>>주 7회</label>
					</div>
				</div>

				<!-- 수정 버튼 -->
				<div>
					<button type="submit" class="w-full bg-blue-600 text-white py-2 rounded">수정하기</button>
				</div>

				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			</form>

			<form method="GET" action="<c:url value='/pwchange' />" class="mt-4">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button type="submit" class="text-blue-600 underline">비밀번호 변경</button>
			</form>
		</sec:authorize>
	</section>

</body>
</html>

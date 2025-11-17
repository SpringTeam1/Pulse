<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<title>회원가입</title>
	<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 py-10">

	<section class="max-w-5xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">
		<h2 class="text-2xl font-bold">회원 정보 입력</h2>

		<form method="POST" action="/pulse/registerok.do" enctype="multipart/form-data" class="space-y-6">
			
			<!-- 프로필 사진 -->
			<div>
				<label class="block font-semibold mb-2">프로필 사진</label>
				<div class="relative w-56 aspect-square mb-3">
					<img id="preview" src="${pageContext.request.contextPath}/asset/pic/${empty adto.profilePhoto ? 'pic.png' : adto.profilePhoto}" alt="프로필 미리보기" class="w-full h-full object-cover rounded shadow cursor-pointer">
					<label class="absolute inset-0 cursor-pointer" for="photo" title="이미지 선택"></label>
				</div>
				<input type="file" id="photo" name="profilePhoto" accept="image/png,image/jpeg" class="hidden">
				<div class="space-x-2">
					<button type="button" class="px-4 py-2 bg-blue-600 text-white rounded" onclick="photo.click()">이미지 선택</button>
					<button type="button" class="px-4 py-2 bg-gray-300 rounded" onclick="resetPhoto()">이미지 삭제</button>
				</div>
			</div>

			<!-- 이메일 -->
			<div>
				<label class="block font-semibold mb-1">이메일</label>
				<div class="flex gap-2 mb-2">
					<input type="text" id="accountId" name="accountId" placeholder="이메일 주소 입력" class="flex-1 border rounded px-3 py-2">
					<button type="button" id="btnSend" class="px-4 py-2 bg-gray-300 rounded">인증번호 발송</button>
				</div>

				<!-- 인증번호 -->
				<div id="verifyWrap" class="flex gap-2 mt-2 hidden">
					<input type="text" id="code" maxlength="6" placeholder="인증번호 6자리" class="flex-1 border rounded px-3 py-2">
					<button type="button" id="btnVerify" class="px-4 py-2 bg-gray-300 rounded">인증하기</button>
				</div>

				<div id="emailMsg" class="text-sm text-red-500 mt-1"></div>
				<input type="hidden" id="emailVerified" name="emailVerified" value="false">
			</div>

			<!-- 암호 -->
			<div>
				<label class="block font-semibold mb-1">암호</label>
				<input type="password" name="password" required class="w-full border rounded px-3 py-2">
			</div>

			<!-- 이름 -->
			<div>
				<label class="block font-semibold mb-1">이름</label>
				<input type="text" id="name" name="name" required class="w-full border rounded px-3 py-2">
			</div>

			<!-- 닉네임 -->
			<div>
				<label class="block font-semibold mb-1">닉네임</label>
				<input type="text" id="nickname" name="nickname" required class="w-full border rounded px-3 py-2">
			</div>

			<!-- 전화번호 -->
			<div>
				<label class="block font-semibold mb-1">전화번호</label>
				<input type="text" name="phoneNum" placeholder="01012345678" required class="w-full border rounded px-3 py-2">
				<p class="text-sm text-gray-500 mt-1">'XXX-XXXX-XXXX'형식으로 입력해주세요.</p>
			</div>

			<!-- 생년월일 -->
			<div>
				<label class="block font-semibold mb-2">생년월일</label>
				<div class="grid grid-cols-3 gap-4">
					<select class="w-full border rounded px-3 py-2" id="yyyy" name="yyyy"></select>
					<select class="w-full border rounded px-3 py-2" id="mm" name="mm"></select>
					<select class="w-full border rounded px-3 py-2" id="dd" name="dd"></select>
				</div>
				<input type="hidden" id="birthday" name="birthday" required>
			</div>

			<!-- 성별 -->
			<div>
				<label class="block font-semibold mb-1">성별</label>
				<div class="flex gap-4">
					<label class="flex items-center gap-2"><input type="radio" name="gender" value="남자" <c:if test="${gender == '남자'}">checked</c:if>>남자</label>
					<label class="flex items-center gap-2"><input type="radio" name="gender" value="여자" <c:if test="${gender == '여자'}">checked</c:if>>여자</label>
				</div>
			</div>

			<!-- 광역시 -->
			<div>
				<label class="block font-semibold mb-1">광역시</label>
				<select id="sido" name="regionCity" class="w-full border rounded px-3 py-2">
					<option value="" <c:if test="${empty regionCity}">selected</c:if>>선택</option>
					<option value="서울특별시" <c:if test="${regionCity == '서울특별시'}">selected</c:if>>서울특별시</option>
					<option value="부산광역시" <c:if test="${regionCity == '부산광역시'}">selected</c:if>>부산광역시</option>
					<option value="대구광역시" <c:if test="${regionCity == '대구광역시'}">selected</c:if>>대구광역시</option>
					<option value="인천광역시" <c:if test="${regionCity == '인천광역시'}">selected</c:if>>인천광역시</option>
				 	<option value="광주광역시" <c:if test="${regionCity == '광주광역시'}">selected</c:if>>광주광역시</option>
					<option value="대전광역시" <c:if test="${regionCity == '대전광역시'}">selected</c:if>>대전광역시</option>
					<option value="울산광역시" <c:if test="${regionCity == '울산광역시'}">selected</c:if>>울산광역시</option>
					<option value="세종특별자치시" <c:if test="${regionCity == '세종특별자치시'}">selected</c:if>>세종특별자치시</option>
					<option value="경기도" <c:if test="${regionCity == '경기도'}">selected</c:if>>경기도</option>
					<option value="강원특별자치도" <c:if test="${regionCity == '강원특별자치도'}">selected</c:if>>강원특별자치도</option>
					<option value="충청북도" <c:if test="${regionCity == '충청북도'}">selected</c:if>>충청북도</option>
					<option value="충청남도" <c:if test="${regionCity == '충청남도'}">selected</c:if>>충청남도</option>
					<option value="전북특별자치도" <c:if test="${regionCity == '전북특별자치도'}">selected</c:if>>전북특별자치도</option>
					<option value="전라남도" <c:if test="${regionCity == '전라남도'}">selected</c:if>>전라남도</option>
					<option value="경상북도" <c:if test="${regionCity == '경상북도'}">selected</c:if>>경상북도</option>
					<option value="경상남도" <c:if test="${regionCity == '경상남도'}">selected</c:if>>경상남도</option>
					<option value="제주특별자치도" <c:if test="${regionCity == '제주특별자치도'}">selected</c:if>>제주특별자치도</option>
				</select>
				<c:if test="${not empty regionCityMsg}">
					<p class="text-sm text-red-500 mt-1">${regionCityMsg}</p>
				</c:if>
			</div>

			<!-- 시군구 -->
			<div>
				<label class="block font-semibold mb-1">시군구</label>
				<input type="text" id="sigungu" name="regionCounty" class="w-full border rounded px-3 py-2">
				<c:if test="${not empty regionCountyMsg}">
					<p class="text-sm text-red-500 mt-1">${regionCountyMsg}</p>
				</c:if>
			</div>

			<!-- 동읍면 -->
			<div>
				<label class="block font-semibold mb-1">동읍면</label>
				<input type="text" id="dong" name="regionDistrict" class="w-full border rounded px-3 py-2">
				<c:if test="${not empty regionDistrictMsg}">
					<p class="text-sm text-red-500 mt-1">${regionDistrictMsg}</p>
				</c:if>
			</div>

			<!-- 운동빈도 -->
			<div>
				<label class="block font-semibold mb-1">운동 빈도</label>
				<div class="flex flex-wrap gap-4">
					<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="0" <c:if test="${exerciseFrequency == '0'}">checked</c:if>>주 1회 미만</label>
					<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="1-3" <c:if test="${exerciseFrequency == '1-3'}">checked</c:if>>주 1~3회</label>
					<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="4-6" <c:if test="${exerciseFrequency == '4-6'}">checked</c:if>>주 4~6회</label>
					<label class="flex items-center gap-2"><input type="radio" name="exerciseFrequency" value="7" <c:if test="${exerciseFrequency == '7'}">checked</c:if>>주 7회</label>
				</div>
			</div>

			<!-- 가입 버튼 -->
			<div>
				<button type="submit" class="w-full bg-blue-600 text-white py-2 rounded">가입하기</button>
			</div>

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		</form>
	</section>

</body>
</html>

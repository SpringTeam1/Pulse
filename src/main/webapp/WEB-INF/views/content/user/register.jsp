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
	
	<h2>회원 정보 입력</h2>
	
	<form method="POST" action="/pulse/registerok.do">
	<table class="vertical">
		<tr>
			<th>프로필 사진</th>
			<td><input type="file" name="profilePhoto" value="pic.png" accept="image/png,image/jpeg" required></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><input type="text" name="accountId" value="test@gmail.com" required></td>
		</tr>
		<tr>
			<th>암호</th>
			<td><input type="password" name="password" value="1111" required></td>
		</tr>
		<tr>
			<th>닉네임</th>
			<td><input type="text" name="nickname" value="테스트" required></td>
		</tr>
		<tr>
			<th>회원가입 유형</th>
			<td><input type="text" name="registerType" value="기본" required></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name" value="테스트" required></td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td><input type="text" name="phoneNum" value="010-1111-1111"required></td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td><input type="text" name="birthday" value="2000.11.11" required></td>
		</tr>
		<tr>
			<th>성별</th>
			<td><input type="text" name="gender" value="남자" required></td>
		</tr>
		<tr>
			<th>광역시</th>
			<td><input type="text" name="regionCity" value="서울특별시" required></td>
		</tr>
		<tr>
			<th>시군구</th>
			<td><input type="text" name="regionCounty" value="강남구" required></td>
		</tr>
		<tr>
			<th>동읍면</th>
			<td><input type="text" name="regionDistrict" value="서초동" required></td>
		</tr>
		<tr>
			<th>운동빈도</th>
			<td><input type="text" name="exerciseFrequency" value="1-3" required></td>
		</tr>
	</table>
	<div>
		<button>가입하기</button>
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	
</body>
</html>















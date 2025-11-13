<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
	<title>Insert title here</title>
<style>
.photo-box{position:relative;width:220px;aspect-ratio:1/1}
.photo-box img{width:100%;height:100%;object-fit:cover;display:block;cursor:pointer}
.photo-box .overlay{position:absolute;inset:0;cursor:pointer} /* 이미지 전체를 덮는 클릭 영역 */
.hint{margin-top:6px;font-size:12px;color:#666}
button,
.btn-main,
.btn-sub {
  cursor: pointer;
}

</style>
</head>
<body>	
	<!-- register.jsp -->
	
	<h2>회원 정보 수정</h2>
	
	<sec:authorize access="isAuthenticated()">
    	<sec:authentication property="principal.adto" var="user"/>
	
		<form method="POST" action="/pulse/infoedit.do">
		<table class="vertical">
			<tr>
				<th>프로필 사진</th>
				<td>
					<!-- 미리보기 영역 -->
				    <div class="photo-box">
				      <img id="preview" src="${pageContext.request.contextPath}/asset/pic/${user.profilePhoto}" alt="프로필 미리보기">
				      <label class="overlay" for="photo" title="이미지 선택"></label>
				    </div>
				
				    <!-- 실제 파일 선택 input -->
				    <input type="file" id="photo" name="profilePhoto" accept="image/png,image/jpeg" style="display:none;">
				
					<button type="button" class="btn btn-main" onclick="photo.click()">이미지 선택</button>
				    <button type="button" class="btn btn-sub" onclick="resetPhoto()">이미지 삭제</button>
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="name" value="${user.name}" required></td>
			</tr>
			<tr>
				<th>닉네임</th>
				<td><input type="text" id="nickname" name="nickname" value="${user.nickname}" required></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="phoneNum" value="${user.phoneNum}" required oninput="this.value = this.value.replace(/[^0-9]/g, '');"></td>
				<td><div class="hint">'-' 없이 숫자로만 입력해주세요.</div></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>
					<div class="col">
						<label>연도</label>
				    	<select class="input" id="yyyy" name="yyyy"></select>
				    </div>
				    <div class="col">
				    	<label>월</label>
				    	<select class="input" id="mm" name="mm"></select>
				    </div>
				    <div class="col">
				    	<label>일</label>
				    	<select class="input" id="dd" name="dd"></select>
				    </div>
				    
				    <input type="hidden" id="birthday" name="birthday" value="${user.birthday}" required>
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<label class="chip"><input type="radio" name="gender" value="남자"<c:if test="${user.gender == '남자'}">checked</c:if>>남자</label>
					<label class="chip"><input type="radio" name="gender" value="여자"<c:if test="${user.gender == '여자'}">checked</c:if>>여자</label>
				</td>
			</tr>
			<tr>
				<th>광역시</th>
				<td>
					<select id="sido" name="regionCity">
					 	<option value="">선택</option>
					 	<option value="서울특별시" <c:if test="${user.regionCity == '서울특별시'}">selected</c:if>>서울특별시</option>
						<option value="부산광역시" <c:if test="${user.regionCity == '부산광역시'}">selected</c:if>>부산광역시</option>
						<option value="대구광역시" <c:if test="${user.regionCity == '대구광역시'}">selected</c:if>>대구광역시</option>
						<option value="인천광역시" <c:if test="${user.regionCity == '인천광역시'}">selected</c:if>>인천광역시</option>
					 	<option value="광주광역시" <c:if test="${user.regionCity == '광주광역시'}">selected</c:if>>광주광역시</option>
						<option value="대전광역시" <c:if test="${user.regionCity == '대전광역시'}">selected</c:if>>대전광역시</option>
						<option value="울산광역시" <c:if test="${user.regionCity == '울산광역시'}">selected</c:if>>울산광역시</option>
						<option value="세종특별자치시" <c:if test="${user.regionCity == '세종특별자치시'}">selected</c:if>>세종특별자치시</option>
						<option value="경기도" <c:if test="${user.regionCity == '경기도'}">selected</c:if>>경기도</option>
						<option value="강원특별자치도" <c:if test="${user.regionCity == '강원특별자치도'}">selected</c:if>>강원특별자치도</option>
						<option value="충청북도" <c:if test="${user.regionCity == '충청북도'}">selected</c:if>>충청북도</option>
						<option value="충청남도" <c:if test="${user.regionCity == '충청남도'}">selected</c:if>>충청남도</option>
						<option value="전북특별자치도" <c:if test="${user.regionCity == '전북특별자치도'}">selected</c:if>>전북특별자치도</option>
						<option value="전라남도" <c:if test="${user.regionCity == '전라남도'}">selected</c:if>>전라남도</option>
						<option value="경상북도" <c:if test="${user.regionCity == '경상북도'}">selected</c:if>>경상북도</option>
						<option value="경상남도" <c:if test="${user.regionCity == '경상남도'}">selected</c:if>>경상남도</option>
						<option value="제주특별자치도" <c:if test="${user.regionCity == '제주특별자치도'}">selected</c:if>>제주특별자치도</option>
					</select>
					<c:if test="${not empty regionCityMsg}">
						<div class="msg err">${regionCityMsg}</div>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>시군구</th>
				<td>
					<input type="text" id="sigungu" name="regionCounty" value="${user.regionCounty}">
					<c:if test="${not empty regionCountyMsg}">
						<div class="msg err">${regionCountyMsg}</div>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>동읍면</th>
				<td>
					<input type="text" id="dong" name="regionDistrict" value="${user.regionDistrict}">
						<c:if test="${not empty regionDistrictMsg}">
							<div class="msg err">${regionDistrictMsg}</div>
						</c:if>
				</td>
			</tr>
			<tr>
				<th>운동빈도</th>
				<td>
					<label class="chip"><input type="radio" name="exerciseFrequency" value="0" <c:if test="${user.exerciseFrequency == '0'}">checked</c:if>> 주 1회 미만</label>
					<label class="chip"><input type="radio" name="exerciseFrequency" value="1-3" <c:if test="${user.exerciseFrequency == '1-3'}">checked</c:if>> 주 1~3회</label>
					<label class="chip"><input type="radio" name="exerciseFrequency" value="4-6" <c:if test="${user.exerciseFrequency == '4-5'}">checked</c:if>> 주 4~6회</label>
					<label class="chip"><input type="radio" name="exerciseFrequency" value="7" <c:if test="${user.exerciseFrequency == '7'}">checked</c:if>> 주 7회</label>
				</td>
			</tr>
		</table>
		<div>
			<button>수정하기</button>
		</div>
	
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		</form>

		<form method="GET" action="<c:url value='/pwchange' />">
	    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	    	<button type="submit" class="button-link">비밀번호 변경</button>
		</form>
	
	</sec:authorize>
	
</body>
</html>















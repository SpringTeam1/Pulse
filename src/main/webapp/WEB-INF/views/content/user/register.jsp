<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
<style>
.photo-box{position:relative;width:220px;aspect-ratio:1/1}
.photo-box img{width:100%;height:100%;object-fit:cover;display:block;cursor:pointer}
.photo-box .overlay{position:absolute;inset:0;cursor:pointer} /* 이미지 전체를 덮는 클릭 영역 */
.hint{margin-top:6px;font-size:12px;color:#666}
.btn-main,
.btn-sub {
  cursor: pointer;
}

</style>
</head>
<body>	
	<!-- register.jsp -->
	
	<h2>회원 정보 입력</h2>
	
	<form method="POST" action="/pulse/registerok.do">
	<table class="vertical">
		<tr>
			<th>프로필 사진</th>
			<td>
				<!-- 미리보기 영역 -->
			    <div class="photo-box">
			      <img id="preview" src="${pageContext.request.contextPath}/asset/pic/pic.png" alt="프로필 미리보기">
			      <label class="overlay" for="photo" title="이미지 선택"></label>
			    </div>
			
			    <!-- 실제 파일 선택 input -->
			    <input type="file" id="photo" name="profilePhoto" accept="image/png,image/jpeg" style="display:none;">
			
				<button type="button" class="btn btn-main" onclick="photo.click()">이미지 선택</button>
			    <button type="button" class="btn btn-sub" onclick="resetPhoto()">이미지 삭제</button>
			</td>
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
	
	
	<script>
	
	//이미지
	//미리보기 + 확장자 검증
	const photo = document.getElementById('photo');
	const preview = document.getElementById('preview');
	photo.addEventListener('change', e=>{
	  const f = e.target.files[0];
	  if(!f) return;
	  const ok = /(png|jpg|jpeg)$/i.test(f.name);
	  if(!ok){ alert('png, jpg, jpeg만 업로드 가능합니다.'); photo.value=''; return; }
	  const url = URL.createObjectURL(f);
	  preview.src = url;
	});
	
	//이미지 초기화
	function resetPhoto() {
		  const preview = document.getElementById('preview');
		  const input = document.getElementById('photo');
		  const hidden = document.getElementById('photoName');

		  preview.src = '${pageContext.request.contextPath}/asset/pic/pic.png';
		  input.value = '';
		  hidden.value = 'pic.png';
		}
	
	</script>
	
</body>
</html>















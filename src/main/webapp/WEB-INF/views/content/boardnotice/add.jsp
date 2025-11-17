<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 
    📌 add.jsp
    - 새 공지 작성 화면
    - form → Controller(/boardnotice/addok.do)로 전송 (POST)
-->

<section class="max-w-4xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">

    <h1 class="text-3xl font-bold text-gray-800">✏️ 새 공지 작성</h1>

    <!-- 
        📌 form 설명
        - action: BoardNoticeController.addok()로 전송
        - method="post" : POST 방식으로 데이터 전송
    -->
    <form action="${pageContext.request.contextPath}/boardnotice/addok.do" 
      	method="post" 
      	enctype="multipart/form-data"
      	class="space-y-5">

        <!-- ⚠️ 실제 서버로 넘어가는 값: accountId -->
        <input type="hidden" name="accountId" value="${accountId}">

        <!-- 작성자(닉네임) 표시용: readonly -->
        <div>
            <label class="text-sm text-gray-600">작성자</label>
            <input type="text" value="${nickname}"
                   class="w-full mt-1 p-2 border rounded-lg bg-gray-100"
                   readonly>
        </div>

        <!-- 제목 -->
        <div>
            <label class="text-sm text-gray-600">제목</label>
            <input type="text" name="title"
                   class="w-full mt-1 p-2 border rounded-lg focus:outline-brand"
                   required>
        </div>

        <!-- 내용 -->
        <div>
            <label class="text-sm text-gray-600">내용</label>
            <textarea name="content" rows="10"
                      class="w-full mt-1 p-2 border rounded-lg focus:outline-brand"
                      required></textarea>
        </div>
        
        <!-- 파일 첨부 -->
        <div>
		    <label class="text-sm text-gray-600">첨부파일</label>
		    <input type="file" name="attach"
		           class="w-full mt-1 p-2 border rounded-lg focus:outline-brand">
		</div>

        <!-- 버튼 -->
        <div class="flex justify-end gap-3">

            <!-- 취소 → 목록으로 이동 -->
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/boardnotice/list.do'"
                    class="px-4 py-2 bg-gray-200 rounded-lg hover:bg-gray-300 transition">
                취소
            </button>

            <!-- 등록 버튼 -->
            <button type="submit"
                    class="px-4 py-2 bg-brand text-white rounded-lg hover:bg-brand-dark transition">
                등록
            </button>
        </div>

    </form>
    
</section>

<script>
    console.log("현재 로그인 계정 ID: '${accountId}'");
    console.log("현재 로그인 닉네임: '${nickname}'");
    console.log("현재 로그인 역할(권한): '${role}'"); // ROLE_ADMIN, ROLE_USER 등으로 출력됨
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 
    📌 view.jsp (상세보기)
    - dto 객체는 Controller가 전달
    - 삭제/수정 모두 Controller 방식 사용
-->

<section class="max-w-4xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">

    <!-- 제목 -->
    <h1 class="text-3xl font-bold text-gray-800">${dto.title}</h1>

    <!-- 글 정보 -->
    <div class="flex justify-between text-gray-500 text-sm">
        <p>작성자: ${dto.nickname}</p>
        <p>작성일: ${dto.regdate}</p>
        <p>조회수: ${dto.readCount}</p>
    </div>
	
    <hr class="my-4">

    <!-- 내용 -->
    <div class="text-gray-800 whitespace-pre-wrap leading-7">
        ${dto.content}
    </div>
    
    <hr class="my-4">
    
    <!-- 첨부한 이미지 -->
    <c:if test="${not empty dto.attach}">
    	<div class="mt-4">
	        <img src="${pageContext.request.contextPath}/boardnoticefiles/${dto.attach}"
	             alt="첨부 이미지"
	             style="max-width: 600px; border-radius: 10px;">
    	</div>
    </c:if>

    <hr class="my-4">

    <!-- 버튼들 -->
    <div class="flex justify-between">

        <!-- 목록 버튼 -->
        <button onclick="location.href='${pageContext.request.contextPath}/boardnotice/list.do'"
                class="px-4 py-2 bg-gray-200 rounded-lg hover:bg-gray-300 transition">
            목록
        </button>

        <div class="flex gap-3">

            <!-- 수정 버튼 -->
            <button onclick="location.href='${pageContext.request.contextPath}/boardnotice/edit.do?seq=${dto.boardSeq}'"
                    class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition">
                ✏️ 수정
            </button>

            <!-- 삭제 버튼 -->
            <form action="${pageContext.request.contextPath}/boardnotice/del.do"
                  method="get"
                  onsubmit="return confirm('정말 삭제하시겠습니까?');">
                <input type="hidden" name="seq" value="${dto.boardSeq}">
                <button class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition">
                    🗑 삭제
                </button>
            </form>

        </div>
    </div>

</section>

<script>
    console.log("현재 로그인 계정 ID: '${sessionScope.accountId}'");
    console.log("현재 로그인 닉네임: '${sessionScope.nickname}'");
    console.log("현재 로그인 역할(권한): '${sessionScope.role}'");
</script>

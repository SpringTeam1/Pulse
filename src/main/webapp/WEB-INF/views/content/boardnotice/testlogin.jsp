<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-md mx-auto mt-20 bg-white rounded-xl shadow p-8 space-y-6">

    <h1 class="text-3xl font-bold text-center text-gray-800">🔐 테스트 로그인</h1>

    <!-- 에러 메시지 -->
    <c:if test="${not empty error}">
        <div class="bg-red-100 text-red-700 p-3 rounded-lg text-center">
            ${error}
        </div>
    </c:if>

    <!-- 로그인 폼 -->
    <form action="${pageContext.request.contextPath}/boardnotice/testloginok.do"
          method="post"
          class="space-y-5">

        <!-- 아이디 -->
        <div>
            <label class="text-sm text-gray-600">아이디</label>
            <input type="text" name="accountId"
                   class="w-full mt-1 p-3 border rounded-lg focus:outline-brand"
                   placeholder="email 입력"
                   required>
        </div>

        <!-- 비밀번호 -->
        <div>
            <label class="text-sm text-gray-600">비밀번호</label>
            <input type="password" name="password"
                   class="w-full mt-1 p-3 border rounded-lg focus:outline-brand"
                   placeholder="비밀번호"
                   required>
        </div>

        <!-- 로그인 버튼 -->
        <button type="submit"
                class="w-full py-3 bg-brand text-white font-semibold rounded-lg hover:bg-brand-dark transition">
            로그인
        </button>
    </form>

    <!-- 샘플 계정 안내 -->
    <div class="text-sm text-gray-600 text-center pt-4">
        <p class="font-semibold">📌 테스트 계정</p>
        <p>관리자: <span class="font-mono">adminhong@naver.com / 1234</span></p>
        <p>일반회원: <span class="font-mono">userhong@naver.com / 1234</span></p>
    </div>

</section>

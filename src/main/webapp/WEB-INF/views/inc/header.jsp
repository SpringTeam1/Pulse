<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<header class="bg-white shadow-md sticky top-0 z-50">
  <div class="w-full px-4 lg:px-6">
    <div class="flex justify-between items-center h-16">
      
      <!-- ✅ 로고 -->
      <a href="${pageContext.request.contextPath}/index.do"
         class="text-2xl font-bold text-brand hover:text-brand-dark transition">
        Pulse🏃‍♂️‍
      </a>

      <!-- ✅ 데스크탑 네비게이션 -->
      <nav class="hidden md:flex space-x-6">
        <!-- 📁 큰 카테고리 (드롭다운 포함) -->
        <div class="relative group">
          <button class="text-gray-700 hover:text-brand font-medium">게시판</button>
          <!-- ▼ 드롭다운 -->
          <div class="absolute hidden group-hover:block bg-white border rounded-lg shadow-lg w-44 top-full left-0">
            <a href="${pageContext.request.contextPath}/boardnotice/list.do" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">공지게시판</a>
            <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">건의게시판</a>
            <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">코스게시판</a>
          </div> 
        </div>

        <!-- ✅ 커스텀 카테고리 추가 가이드
          - 아래 예시처럼 새로운 대메뉴 추가 가능
          - 링크는 Tiles 정의된 jsp로 연결 가능 (예: /crew.do → crew.jsp)
        -->
        <!--
        <div class="relative group">
          <button class="text-gray-700 hover:text-brand font-medium">Services</button>
          <div class="absolute hidden group-hover:block bg-white border rounded-lg shadow-lg mt-2 w-44">
            <a href="#" class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">Consulting</a>
            <a href="#" class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">Development</a>
          </div>
        </div>
        -->
		
		<!-- 해당하는 도메인 작성하기 -->
        <a href="${pageContext.request.contextPath}/crewmain.do" class="text-gray-700 hover:text-brand font-medium">크루</a>
        <a href="${pageContext.request.contextPath}/crewmain.do" class="text-gray-700 hover:text-brand font-medium">코스</a>
      </nav>
      
      <!-- ✅ 로그인한 사람의 아이디 표시 (임시용) -->
        <div class="flex items-center space-x-3">
            <div class="hidden md:flex items-center bg-brand/10 text-brand text-sm font-semibold px-3 py-1 rounded-full shadow-sm border border-brand/20">
                <span>현재 로그인:</span>
                <span class="ml-1 text-brand-dark">
            <%= session.getAttribute("accountId") != null ? session.getAttribute("accountId") : "게스트" %>
          </span>
        </div>

      <!-- ✅ 모바일 햄버거 버튼 -->
      <div class="md:hidden">
        <button id="menuBtn" class="text-gray-600 hover:text-brand focus:outline-none">
          <svg class="w-7 h-7" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 12h16M4 18h16" />
          </svg>
        </button>
      </div>
    </div>
  </div>

  <!-- ✅ 모바일 메뉴 -->
  <div id="mobileMenu" class="hidden md:hidden bg-white border-t">
    <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">게시판</a>
    <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">크루</a>
    <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">코스</a>
  </div>
</header>

<!-- ✅ JS: 모바일 메뉴 토글 -->
<script>
  document.addEventListener("DOMContentLoaded", () => {
    const btn = document.getElementById("menuBtn");
    const menu = document.getElementById("mobileMenu");
    if (btn && menu) {
      btn.addEventListener("click", () => menu.classList.toggle("hidden"));
    }
  });
</script>

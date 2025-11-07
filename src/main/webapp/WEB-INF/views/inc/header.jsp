<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Tails Header</title>
  <link href="${pageContext.request.contextPath}/asset/css/output.css" rel="stylesheet">
  <!-- ✅ Tailwind CDN 
  <script src="https://cdn.tailwindcss.com"></script>

  <!-- ✅ Optional: Tailwind 커스터마이즈 (색상 / 폰트 등)
  <script>
    tailwind.config = {
      theme: {
        extend: {
          colors: {
            brand: {
              DEFAULT: '#1DA1F2', 
              dark: '#4338CA'
            }
          },  
          fontFamily: {
            display: ['"Poppins"', 'sans-serif']
          }
        }
      }
    }
  </script> -->
</head>

<body class="bg-gray-50 font-display">

<!-- 🌈 HEADER -->
<header class="bg-white shadow-md sticky top-0 z-50">
  <div class="w-full px-4 lg:px-6">
    <div class="flex justify-between items-center h-16">
      
      <!-- ✅ 로고 -->
      <a href="${pageContext.request.contextPath}/index.jsp"
         class="text-2xl font-bold text-brand hover:text-brand-dark transition">
        Pulse🏃‍♂️‍
      </a>

      <!-- ✅ 데스크탑 네비게이션 -->
      <nav class="hidden md:flex space-x-6">
        <!-- 📁 큰 카테고리 (드롭다운 포함) -->
        <div class="relative group">
          <button class="text-gray-700 hover:text-brand font-medium">Products</button>
          <!-- ▼ 드롭다운 -->
          <div class="absolute hidden group-hover:block bg-white border rounded-lg shadow-lg mt-2 w-44">
            <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">Overview</a>
            <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">Pricing</a>
            <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">FAQ</a>
          </div>
        </div>

        <!-- ✅ 커스텀 카테고리 추가 예시 -->
        <!--
        <div class="relative group">
          <button class="text-gray-700 hover:text-brand font-medium">Services</button>
          <div class="absolute hidden group-hover:block bg-white border rounded-lg shadow-lg mt-2 w-44">
            <a href="#" class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">Consulting</a>
            <a href="#" class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">Development</a>
          </div>
        </div>
        -->

        <a href="${pageContext.request.contextPath}/about.jsp" class="text-gray-700 hover:text-brand font-medium">About</a>
        <a href="${pageContext.request.contextPath}/contact.jsp" class="text-gray-700 hover:text-brand font-medium">Contact</a>
      </nav>

      <!-- ✅ 모바일 햄버거 버튼 -->
      <div class="md:hidden">
        <button id="menuBtn" class="text-gray-600 hover:text-brand focus:outline-none">
          <!-- 햄버거 아이콘 -->
          <svg class="w-7 h-7" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 12h16M4 18h16" />
          </svg>
        </button>
      </div>
    </div>
  </div>

  <!-- ✅ 모바일 메뉴 -->
  <div id="mobileMenu" class="hidden md:hidden bg-white border-t">
    <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">Products</a>
    <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">About</a>
    <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">Contact</a>
  </div>
</header>

<!-- ✅ JS: 모바일 메뉴 토글 -->
<script>
  document.getElementById("menuBtn").addEventListener("click", () => {
    const menu = document.getElementById("mobileMenu");
    menu.classList.toggle("hidden");
  });
</script>

</body>
</html>

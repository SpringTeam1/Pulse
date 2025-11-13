<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- =====================================================================================================
 ✅ Dashboard.jsp (Free Dashboard UI Kit 스타일 적용)
 - Tiles content 영역 전용 JSP
 - Tailwind 반응형 기반 / 카드형 + 그래프형 위젯 구조
 - 컨트롤러에서 model에 top3Courses, list, pendingCount 등 데이터 전달
===================================================================================================== -->

<section class="max-w-7xl mx-auto mt-10 space-y-10">

  <!-- ====================== 🔹 HEADER ====================== -->
  <header class="flex flex-col md:flex-row justify-between items-center gap-4">
    <div>
      <h1 class="text-4xl font-bold text-brand">📊 관리자 대시보드</h1>
      <p class="text-gray-500 mt-1">사이트 활동, 커뮤니티 상태, 회원 통계를 한눈에 확인하세요.</p>
    </div>
    <div class="flex items-center gap-2">
      <input type="text" placeholder="Search..."
             class="border border-gray-200 rounded-lg px-4 py-2 text-sm focus:ring-2 focus:ring-brand focus:outline-none w-64">
      <img src="${pageContext.request.contextPath}/asset/pic/pic.png" alt="관리자 프로필" class="w-10 h-10 rounded-full shadow">
    </div>
  </header>

  <!-- ====================== 🔹 OVERVIEW CARDS ====================== -->
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
    <div class="bg-gradient-to-r from-brand to-brand-dark text-white rounded-2xl shadow p-6">
      <h3 class="text-sm opacity-80">오늘 접속자</h3>
      <p class="text-3xl font-bold mt-2">1,256</p>
      <span class="text-xs opacity-70">+12% vs 어제</span>
    </div>

    <div class="bg-white rounded-2xl shadow p-6">
      <h3 class="text-sm text-gray-500">신규 회원</h3>
      <p class="text-3xl font-bold text-brand mt-2">342</p>
      <span class="text-xs text-green-600 font-medium">▲ 5% 증가</span>
    </div>

    <div class="bg-white rounded-2xl shadow p-6">
      <h3 class="text-sm text-gray-500">오늘 게시글</h3>
      <p class="text-3xl font-bold text-brand mt-2">98</p>
      <span class="text-xs text-red-500 font-medium">▼ 2% 감소</span>
    </div>

    <div class="bg-white rounded-2xl shadow p-6">
      <h3 class="text-sm text-gray-500">총 매출</h3>
      <p class="text-3xl font-bold text-brand mt-2">$5,340</p>
      <span class="text-xs text-green-600 font-medium">+8% 이번주</span>
    </div>
  </div>

  <!-- ====================== 🔹 GRID LAYOUT ====================== -->
  <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">

    <!-- 🔥 러닝 활동 -->
    <div class="bg-white rounded-2xl shadow p-6">
      <h2 class="text-2xl font-bold text-brand mb-4">🔥 러닝 활동</h2>

      <h3 class="text-lg font-semibold text-gray-700 mb-2">오늘의 인기 코스 TOP3</h3>
      <ul class="space-y-2 text-gray-600">
      
        <c:forEach items="${top3Courses}" var="dto">
          <li class="flex justify-between items-center border-b py-1">
            <a href="/pulse/course/detail/${dto.courseSeq}"
               class="text-brand font-medium hover:text-brand-dark transition">
              ${dto.courseName}
            </a>
            <%-- <span class="text-xs text-gray-400">참여 ${dto.participants}명</span> --%>
          </li>
        </c:forEach>
        
        
      </ul>

      <div class="mt-6">
        <h3 class="text-lg font-semibold text-gray-700 mb-3">주간 활동 그래프</h3>
        <div class="w-full h-56">
          <canvas id="weeklyChart"></canvas>
        </div>
      </div>
    </div>

    <!-- 💬 커뮤니티 관리 -->
    <div class="bg-white rounded-2xl shadow p-6">
      <h2 class="text-2xl font-bold text-brand mb-4">💬 커뮤니티 관리</h2>

      <!-- 미처리 리스트 -->
      <div>
        <h3 class="text-lg font-semibold text-gray-700 mb-2">미처리 리스트</h3>
        <ul class="space-y-1 text-gray-600">
          <li>🎯 <a href="#" class="text-brand hover:text-brand-dark">신고</a> : 1</li>
          <li>❓ <a href="#" class="text-brand hover:text-brand-dark">문의</a> : 13</li>

          <c:set var="suggestionCount" value="0"/>
          <c:forEach items="${list}" var="dto">
            <c:if test="${dto.boardContentSeq != null}">
              <c:set var="suggestionCount" value="${suggestionCount + 1}" />
            </c:if>
          </c:forEach>
          <li>📜 <a href="#" class="text-brand hover:text-brand-dark">건의</a> : ${suggestionCount}</li>
        </ul>
      </div>

      <!-- 승인 대기 -->
      <div class="mt-6">
        <h3 class="text-lg font-semibold text-gray-700 mb-2">승인 대기 현황</h3>

        <c:set var="pendingCount" value="0" />
        <c:forEach items="${list}" var="dto">
          <c:if test="${dto.courseApproval == '대기'}">
            <c:set var="pendingCount" value="${pendingCount + 1}" />
          </c:if>
        </c:forEach>

        <ul class="space-y-1 text-gray-600">
          <li>👥 <a href="#" class="text-brand hover:text-brand-dark">크루</a> : 3</li>
          <li>📍 <a href="#" class="text-brand hover:text-brand-dark">코스</a> : ${pendingCount}</li>
        </ul>
      </div>
    </div>

    <!-- 🌏 회원 통계 -->
    <div class="bg-white rounded-2xl shadow p-6">
      <h2 class="text-2xl font-bold text-brand mb-4">🌏 회원 통계</h2>
      <h3 class="text-lg font-semibold text-gray-700 mb-3">등급별 회원 비율</h3>
      <div class="w-full h-64">
        <canvas id="memberChart"></canvas>
      </div>
    </div>
  </div>
</section>









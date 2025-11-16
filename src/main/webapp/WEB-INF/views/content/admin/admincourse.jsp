<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ==========================================================================
    코스 신청 관리 페이지 (Tailwind + Dashboard 스타일)
    - Controller: model.addAttribute("list", courseList);
    - Tiles content 영역으로 include됨
=========================================================================== -->

<section class="max-w-7xl mx-auto mt-10 space-y-10">

  <!-- ====================== 🔹 HEADER ====================== -->
  <header class="flex flex-col md:flex-row justify-between items-center gap-4">
    <div>
      <h1 class="text-4xl font-bold text-brand">📍 코스 신청 관리</h1>
      <p class="text-gray-500 mt-1">신청된 코스를 승인하거나 대기 목록을 확인할 수 있습니다.</p>
    </div>
  </header>


  <!-- ====================== 🔹 승인 대기 카드 ====================== -->
  <div class="bg-white rounded-2xl shadow p-6">

    <h2 class="text-2xl font-bold text-brand mb-4">⏳ 승인 대기 중 (${pendingCount}건)</h2>

    <table class="w-full table-auto border-collapse">
      <thead>
        <tr class="bg-gray-100 text-gray-600 text-sm">
          <th class="py-3 px-4 text-left">코스명</th>
          <th class="py-3 px-4 text-left">작성자(ID)</th>
          <th class="py-3 px-4 text-left">상태</th>
        </tr>
      </thead>
      <tbody class="text-gray-700">

        <c:forEach items="${list}" var="dto">
          <c:if test="${dto.courseApproval == '대기'}">
            <tr class="border-b hover:bg-gray-50 transition">
              <td class="py-3 px-4 font-medium text-brand">
                <a href="#" class="hover:text-brand-dark">${dto.courseName}</a>
              </td>
              <td class="py-3 px-4">${dto.accountId}</td>
              <td class="py-3 px-4 text-yellow-600 font-semibold">${dto.courseApproval}</td>
            </tr>
          </c:if>
        </c:forEach>

      </tbody>
    </table>

  </div>


  <!-- ====================== 🔹 승인 완료 카드 ====================== -->
  <div class="bg-white rounded-2xl shadow p-6">

    <h2 class="text-2xl font-bold text-brand mb-4">✅ 승인 완료 목록</h2>

    <table class="w-full table-auto border-collapse">
      <thead>
        <tr class="bg-gray-100 text-gray-600 text-sm">
          <th class="py-3 px-4 text-left">코스명</th>
          <th class="py-3 px-4 text-left">작성자(ID)</th>
          <th class="py-3 px-4 text-left">상태</th>
        </tr>
      </thead>
      <tbody class="text-gray-700">

        <c:forEach items="${list}" var="dto">
          <c:if test="${dto.courseApproval == '승인'}">
            <tr class="border-b hover:bg-gray-50 transition">
              <td class="py-3 px-4">${dto.courseName}</td>
              <td class="py-3 px-4">${dto.accountId}</td>
              <td class="py-3 px-4 text-green-600 font-semibold">${dto.courseApproval}</td>
            </tr>
          </c:if>
        </c:forEach>

      </tbody>
    </table>

  </div>

</section>

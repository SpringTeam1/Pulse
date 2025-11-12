<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-5xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">

  <!-- 제목 -->
  <div class="border-b pb-4">
    <h1 class="text-3xl font-bold text-black">${dto.title}</h1>
    <div class="flex justify-between items-center text-gray-500 text-sm mt-2">
      <p>${dto.accountId}</p>
      <p>${dto.regdate}</p>
    </div>
  </div>

  <!-- 본문 -->
  <div class="text-gray-800 leading-relaxed whitespace-pre-line min-h-[200px]">
    ${dto.content}
  </div>

  <!-- 첨부파일 -->
  <c:if test="${not empty dto.attach}">
    <div class="bg-gray-50 border rounded-lg p-4">
      <p class="text-sm text-gray-600">📎 첨부파일</p>
      <a href="${pageContext.request.contextPath}/upload/${dto.attach}" class="text-brand font-semibold hover:underline">
        ${dto.attach}
      </a>
    </div>
  </c:if>

  <!-- 하단 버튼 -->
  <div class="flex justify-end gap-2 border-t pt-6">
  <a href="${pageContext.request.contextPath}/boardsuggestion/list"
       class="px-4 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition">목록</a>
       
    <a href="${pageContext.request.contextPath}/boardsuggestion/edit?boardContentSeq=${dto.boardContentSeq}"
       class="px-4 py-2 bg-brand text-white rounded-lg hover:bg-brand-dark transition">수정</a>

    <a href="${pageContext.request.contextPath}/boardsuggestion/del?boardContentSeq=${dto.boardContentSeq}"
       class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-700 transition">삭제</a>
  </div>

</section>

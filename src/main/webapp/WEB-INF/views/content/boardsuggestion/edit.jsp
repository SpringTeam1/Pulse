<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-6xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">

<!-- 제목 -->
  <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
    <h1 class="text-3xl font-bold text-black">📋 ${dto.title}</h1>
  </div>

  <!-- 설명 -->
  <p class="text-gray-600 text-sm">
    ${dto.content}
  </p>

  <!-- ✅ 테이블 (데스크탑 전용) -->
  <div class="hidden md:block overflow-x-auto rounded-lg border border-gray-200">
    <table class="w-full text-left border-collapse">
      <thead class="bg-gray-50 text-gray-600 text-sm uppercase font-semibold">
        <tr>
          <th class="px-6 py-3 w-16 text-center">No</th>
        </tr>
      </thead>
      <tbody class="text-gray-700 text-sm divide-y divide-gray-100">
          <tr class="hover:bg-gray-50 transition">
            <td class="px-6 py-3 text-center">${dto.boardContentSeq}</td>
            <td class="px-6 py-3">
            <a href="${pageContext.request.contextPath}/boardsuggestion/view?boardContentSeq=${dto.boardContentSeq}"
                 class="text-brand hover:text-brand-dark font-medium">
                
                ${dto.title}
            </a>
            </td>
            <td class="px-6 py-3">${dto.adto.name}</td>
            <td class="px-6 py-3">${dto.regdate}</td>
            <td class="px-6 py-3 text-center">${dto.readCount}</td>
          </tr>
      </tbody>
    </table>
  </div>

  <!-- ✅ 모바일 카드형 (md 미만 화면용) -->
  <div class="grid grid-cols-1 gap-4 md:hidden">
      <div class="bg-gray-50 border rounded-lg p-4 shadow-sm hover:shadow transition">
        <a href="${pageContext.request.contextPath}/boardsuggestion/view?boardContentSeq=${dto.boardContentSeq}">
          <h2 class="font-semibold text-brand mb-1">${dto.title}</h2>
        </a>
        <p class="text-gray-500 text-sm">${dto.adto.name} · ${dto.regdate}</p>
        <p class="text-xs text-gray-400 mt-2">조회수 ${dto.readCount}</p>
      </div>
  </div>

    <!-- 수정/삭제 버튼 -->
    <a href="${pageContext.request.contextPath}/boardsuggestion/edit?boardContentSeq=${dto.boardContentSeq}"
       class="px-4 py-2 bg-brand text-white font-semibold rounded-lg hover:bg-brand-dark transition">
      📝 수정
    </a>
    
    <a href="${pageContext.request.contextPath}/boardsuggestion/del?boardContentSeq=${dto.boardContentSeq}"
       class="px-4 py-2 bg-brand text-white font-semibold rounded-lg hover:bg-brand-dark transition">
      🗑️ 삭제
    </a>

    <a href="${pageContext.request.contextPath}/boardsuggestion/list"
       class="px-4 py-2 bg-brand text-gray-500 rounded-lg hover:bg-gray-300 transition">목록보기</a>

    
</section>



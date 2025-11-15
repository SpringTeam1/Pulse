<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-2xl mx-auto mt-20 bg-white rounded-xl shadow p-8 text-center space-y-6">

  <h1 class="text-3xl font-bold text-red-600">⚠️ 게시글 삭제</h1>
  <p class="text-gray-700 text-sm">
    아래 게시글을 정말 삭제하시겠습니까?<br>
    삭제 후에는 복구할 수 없습니다.
  </p>

  <div class="bg-gray-50 border rounded-lg p-4 text-left space-y-1">
    <p><span class="font-semibold">제목:</span> ${dto.title}</p>
    <p><span class="font-semibold">작성자:</span> ${dto.adto.name}</p>
    <p><span class="font-semibold">작성일:</span> ${dto.regdate}</p>
  </div>

  <form method="post" action="${pageContext.request.contextPath}/boardsuggestion/delok" class="pt-4">
    <input type="hidden" name="boardContentSeq" value="${dto.boardContentSeq}">
    <div class="flex justify-center gap-4 mt-6">
      <a href="${pageContext.request.contextPath}/boardsuggestion/view?boardContentSeq=${dto.boardContentSeq}"
         class="px-5 py-2 border rounded-lg hover:bg-gray-100">취소</a>
      <button type="submit"
              class="px-5 py-2 bg-red-500 text-white font-semibold rounded-lg hover:bg-red-600">삭제하기</button>
    </div>
  </form>
</section>



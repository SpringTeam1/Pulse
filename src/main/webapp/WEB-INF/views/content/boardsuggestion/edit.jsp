<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-5xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">
  <h1 class="text-3xl font-bold text-black">✏️ 게시글 수정</h1>

  <form method="POST" action="${pageContext.request.contextPath}/boardsuggestion/editok" enctype="multipart/form-data" class="space-y-6">

    <input type="hidden" name="boardContentSeq" value="${dto.boardContentSeq}" />

    <div>
      <label class="block text-gray-700 font-semibold mb-2">제목</label>
      <input type="text" name="title" value="${dto.title}" class="w-full border rounded-lg p-3 focus:ring-2 focus:ring-brand focus:outline-none" required />
    </div>

    <div>
      <label class="block text-gray-700 font-semibold mb-2">내용</label>
      <textarea name="content" rows="10" class="w-full border rounded-lg p-3 focus:ring-2 focus:ring-brand focus:outline-none" required>${dto.content}</textarea>
    </div>

    <div>
      <label class="block text-gray-700 font-semibold mb-2">첨부파일</label>
      <c:if test="${not empty dto.attach}">
        <p class="text-sm text-gray-600 mb-2">현재 파일: ${dto.attach}</p>
      </c:if>
      <input type="file" name="attach" class="block w-full text-sm text-gray-600" />
    </div>

    <div class="flex justify-end gap-3 pt-4 border-t">
      <button type="submit" class="px-4 py-2 bg-yellow-400 text-white rounded-lg hover:bg-yellow-500 transition">수정완료</button>
      <a href="${pageContext.request.contextPath}/boardsuggestion/view?boardContentSeq=${dto.boardContentSeq}" class="px-4 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition">취소</a>
    </div>

  </form>
  
  
</section>



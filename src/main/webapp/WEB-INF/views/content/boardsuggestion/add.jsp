<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-3xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">

  <h1 class="text-3xl font-bold text-black">✏️ 건의글 작성</h1>
  <p class="text-gray-600 text-sm">새로운 건의사항을 작성해주세요.</p>

  <form method="POST" action="${pageContext.request.contextPath}/boardsuggestion/addok" enctype="multipart/form-data" class="space-y-5">

    <div>
      <label class="block text-gray-700 font-medium mb-2">제목</label>
      <input type="text" name="title" required
             class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-brand/30">
    </div>

    <div>
      <label class="block text-gray-700 font-medium mb-2">내용</label>
      <textarea name="content" rows="10" required
                class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-brand/30"></textarea>
    </div>

    <div>
      <label class="block text-gray-700 font-medium mb-2">첨부파일</label>
      <input type="file" name="attach" class="w-full text-sm text-gray-600">
    </div>

    <div class="flex justify-end gap-2 pt-4">
      <a href="${pageContext.request.contextPath}/boardsuggestion/list"
         class="px-4 py-2 border rounded-lg hover:bg-gray-100">취소</a>
      <button type="submit" 
              class="px-4 py-2 bg-brand text-white rounded-lg hover:bg-brand-dark">등록</button>
    </div>
  </form>
</section>



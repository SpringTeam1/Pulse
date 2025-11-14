<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-4xl mx-auto mt-12 bg-white rounded-2xl shadow p-10 space-y-8">

    <!-- ✅ 제목 -->
    <div class="space-y-3">
        <h1 class="text-4xl font-extrabold text-gray-900 leading-tight">게시글 수정</h1>
        <p class="text-gray-500 text-sm">수정 후 저장 버튼을 눌러주세요.</p>
    </div>

    <!-- ✅ 수정 폼 -->
    <form id="editForm"
          method="POST"
          action="${pageContext.request.contextPath}/api/v1/boardsuggestion/edit/{boardContentSeq}"
          enctype="multipart/form-data"
          class="space-y-8">

        <input type="hidden" name="boardContentSeq" value="${dto.boardContentSeq}" />

        <!-- 제목 -->
        <div>
            <label class="block font-semibold text-gray-800 mb-2">제목</label>
            <input type="text"
                   name="title"
                   value="${dto.title}"
                   class="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-brand"
                   required />
        </div>

        <!-- 내용 -->
        <div>
            <label class="block font-semibold text-gray-800 mb-2">내용</label>
            <textarea name="content"
                      rows="10"
                      class="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-brand text-gray-800"
                      required>${dto.content}</textarea>
        </div>

        <!-- 첨부파일 -->
        <div class="space-y-3">
            <label class="block font-semibold text-gray-800 mb-2">첨부파일</label>

            <c:if test="${not empty dto.attach}">
                <div class="bg-gray-50 rounded-xl p-4 border border-gray-200 flex items-center justify-between">
                    <div class="text-gray-700">
                        📎 현재 첨부:
                        <a href="${pageContext.request.contextPath}/boardsuggestion/${dto.attach}"
                           target="_blank"
                           class="underline text-blue-600 hover:text-blue-800">${dto.attach}</a>
                    </div>

                    <label class="cursor-pointer px-4 py-2 bg-gray-100 hover:bg-gray-200 rounded-lg text-sm font-medium">
                        <input type="file" name="attach" class="hidden" /> 변경
                    </label>
                </div>
            </c:if>

            <c:if test="${empty dto.attach}">
                <input type="file"
                       name="attach"
                       class="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-brand" />
            </c:if>
        </div>

        <!-- 버튼 -->
        <div class="flex justify-end gap-3 pt-4 border-t border-gray-200">

            <button type="button"
                    onclick="location.href='${pageContext.request.contextPath}/boardsuggestion/view?boardContentSeq=${dto.boardContentSeq}'"
                    class="px-5 py-2.5 rounded-lg bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium transition">
                취소
            </button>

            <button type="submit"
                    class="px-5 py-2.5 rounded-lg bg-brand text-white font-medium hover:bg-brand-dark shadow-md hover:shadow-lg transition">
                저장
            </button>
        </div>

    </form>
</section>

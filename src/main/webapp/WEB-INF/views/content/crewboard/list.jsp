<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-6xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">

    <!-- 제목 -->
    <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <h1 class="text-4xl font-bold text-brand">
          ${crewName}
        </h1>
    </div>

    <p class="text-gray-600 text-sm">
        전체 게시글을 최신순으로 확인할 수 있습니다.
    </p>

    <!-- ✅ 테이블 -->
    <div class="block overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-left border-collapse">
            <thead class="bg-gray-50 text-gray-600 text-sm uppercase font-semibold">
            <tr>
                <th class="px-6 py-3 text-center w-12">#</th>
                <th class="px-6 py-3 text-center">제목</th>
                <th class="px-6 py-3 w-32 text-center">작성자</th>
                <th class="px-6 py-3 w-36 text-center">작성일</th>
                <th class="px-6 py-3 w-24 text-center">조회수</th>
                <th class="px-6 py-3 w-24 text-center">좋아요</th>
            </tr>
            </thead>
            <tbody id="board-body" class="text-gray-700 text-sm divide-y divide-gray-100">
            <tr><td colspan="6" class="text-center text-gray-400 py-6">불러오는 중...</td></tr>
            </tbody>
        </table>
    </div>

    <!-- ✅ 페이지네이션 -->
    <div id="pagebar" class="flex justify-center items-center mt-8 space-x-2"></div>

    <!-- ✅ 글쓰기 버튼 -->
    <div class="flex justify-end">
        <a href="${pageContext.request.contextPath}/crewboard/add"
           class="px-4 py-2 bg-brand text-white font-semibold rounded-lg hover:bg-brand-dark transition">
            ✏️ 글쓰기
        </a>
    </div>
</section>


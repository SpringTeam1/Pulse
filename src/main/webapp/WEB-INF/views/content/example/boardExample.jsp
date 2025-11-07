<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- ======================================================================
  ✅ [게시판 목록 페이지 가이드라인]
  - Tiles layout 안의 content 영역 전용 JSP입니다.
  - <html>, <head>, <body> 절대 사용 금지.
  - Tailwind를 이용해 반응형 테이블 + 카드형 레이아웃을 구성했습니다.
  - Controller에서 Model에 boardList(List<BoardDTO>)를 전달받아 JSTL로 출력 가능.
====================================================================== -->

<section class="max-w-6xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">
  <!-- 제목 -->
  <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
    <h1 class="text-3xl font-bold text-black">📋 게시판 목록</h1>
  </div>

  <!-- 설명 -->
  <p class="text-gray-600 text-sm">
    전체 게시글을 최신순으로 볼 수 있습니다.
  </p>

  <!-- ✅ 테이블 (데스크탑 전용) -->
  <div class="hidden md:block overflow-x-auto rounded-lg border border-gray-200">
    <table class="w-full text-left border-collapse">
      <thead class="bg-gray-50 text-gray-600 text-sm uppercase font-semibold">
        <tr>
          <th class="px-6 py-3 w-16 text-center">No</th>
          <th class="px-6 py-3">제목</th>
          <th class="px-6 py-3 w-40">작성자</th>
          <th class="px-6 py-3 w-40">작성일</th>
          <th class="px-6 py-3 w-24 text-center">조회수</th>
          <th class="px-6 py-3 w-24 text-center">좋아요</th>
        </tr>
      </thead>
      <tbody class="text-gray-700 text-sm divide-y divide-gray-100">
        <c:forEach var="board" items="${boardList}">
          <tr class="hover:bg-gray-50 transition">
            <td class="px-6 py-3 text-center">${board.seq}</td>
            <td class="px-6 py-3">
              <a href="${pageContext.request.contextPath}/board/detail.do?seq=${board.seq}"
                 class="text-brand hover:text-brand-dark font-medium">
                ${board.title}
              </a>
            </td>
            <td class="px-6 py-3">${board.writer}</td>
            <td class="px-6 py-3">${board.regdate}</td>
            <td class="px-6 py-3 text-center">${board.readcount}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- ✅ 모바일 카드형 (md 미만 화면용) -->
  <div class="grid grid-cols-1 gap-4 md:hidden">
    <c:forEach var="board" items="${boardList}">
      <div class="bg-gray-50 border rounded-lg p-4 shadow-sm hover:shadow transition">
        <a href="${pageContext.request.contextPath}/board/detail.do?seq=${board.seq}">
          <h2 class="font-semibold text-brand mb-1">${board.title}</h2>
        </a>
        <p class="text-gray-500 text-sm">${board.writer} · ${board.regdate}</p>
        <p class="text-xs text-gray-400 mt-2">조회수 ${board.readcount}</p>
        <p class="text-xs text-gray-400 mt-2">좋아요 ${board.like}</p>
      </div>
    </c:forEach>
  </div>

  <!-- ✅ 페이징 -->
  <div class="flex justify-center items-center mt-8 space-x-2">
    <a href="#" class="px-3 py-1 rounded-lg border text-gray-500 hover:bg-gray-100">«</a>
    <a href="#" class="px-3 py-1 rounded-lg bg-brand text-white">1</a>
    <a href="#" class="px-3 py-1 rounded-lg border text-gray-500 hover:bg-gray-100">2</a>
    <a href="#" class="px-3 py-1 rounded-lg border text-gray-500 hover:bg-gray-100">3</a>
    <a href="#" class="px-3 py-1 rounded-lg border text-gray-500 hover:bg-gray-100">»</a>
  </div>
    <!-- 새 글 작성 버튼 -->
    <a href="${pageContext.request.contextPath}/board/write.do"
       class="px-4 py-2 bg-brand text-white font-semibold rounded-lg hover:bg-brand-dark transition">
      ✏️ 새 글 작성
    </a>
</section>

<!-- JS: 이후 AJAX 목록 업데이트, 정렬 버튼 등 추가 가능 -->
<script>
  document.addEventListener("DOMContentLoaded", () => {
    console.log("📄 boardList.jsp loaded");
  });
</script>

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
    <h1 class="text-3xl font-bold text-black">📋 건의 게시판</h1>
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
        <c:forEach items="${list}" var="dto">
          <tr class="hover:bg-gray-50 transition">
            <td class="px-6 py-3 text-center">${dto.boardContentSeq}</td>
            <td class="px-6 py-3">
            <%-- 
              <a href="${pageContext.request.contextPath}/board/view.do?seq=${dto.boardContentSeq}"
                 class="text-brand hover:text-brand-dark font-medium">
                  --%>
                ${dto.title}
              <!-- </a> -->
            </td>
            <td class="px-6 py-3">${dto.adto.name}</td>
            <td class="px-6 py-3">${dto.regdate}</td>
            <td class="px-6 py-3 text-center">${dto.readCount}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- ✅ 모바일 카드형 (md 미만 화면용) -->
  <div class="grid grid-cols-1 gap-4 md:hidden">
    <c:forEach var="dto" items="${list}">
      <div class="bg-gray-50 border rounded-lg p-4 shadow-sm hover:shadow transition">
        <a href="${pageContext.request.contextPath}/board/detail.do?seq=${dto.boardContentSeq}">
          <h2 class="font-semibold text-brand mb-1">${dto.title}</h2>
        </a>
        <p class="text-gray-500 text-sm">${dto.adto.name} · ${dto.regdate}</p>
        <p class="text-xs text-gray-400 mt-2">조회수 ${dto.readCount}</p>
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



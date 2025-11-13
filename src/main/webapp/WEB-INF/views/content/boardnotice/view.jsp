<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 
    📌 view.jsp (상세보기)
    - dto 객체는 Controller가 전달
    - 삭제는 RestController 호출(form POST)
    - Tailwind 스타일 적용
-->

<section class="max-w-4xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">

    <!-- 제목 -->
    <h1 class="text-3xl font-bold text-gray-800">${dto.title}</h1>

    <!-- 글 정보 -->
    <div class="flex justify-between text-gray-500 text-sm">
        <p>작성자: ${dto.writer}</p>
        <p>작성일: ${dto.regdate}</p>
        <p>조회수: ${dto.readCount}</p>
    </div>

    <hr class="my-4">

    <!-- 내용 -->
    <div class="text-gray-800 whitespace-pre-wrap leading-7">
        ${dto.content}
    </div>

    <hr class="my-4">

    <!-- 버튼들 -->
    <div class="flex justify-between">

        <!-- 왼쪽: 목록 버튼 -->
        <button onclick="location.href='/pulse/boardnotice/list.do'"
                class="px-4 py-2 bg-gray-200 rounded-lg hover:bg-gray-300 transition">
            목록
        </button>

        <!-- 오른쪽: 수정 + 삭제 -->
        <div class="flex gap-3">

            <!-- 수정 -->
            <button onclick="location.href='/pulse/boardnotice/edit.do?seq=${dto.boardSeq}'"
                    class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition">
                ✏️ 수정
            </button>

            <!-- 삭제 (RestController POST 호출) -->
            <form action="/pulse/api/boardnotice/del" method="post"
                  onsubmit="return confirm('정말 삭제하시겠습니까?');">
                <input type="hidden" name="seq" value="${dto.boardSeq}">
                <button class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition">
                    🗑 삭제
                </button>
            </form>

        </div>
    </div>

</section>

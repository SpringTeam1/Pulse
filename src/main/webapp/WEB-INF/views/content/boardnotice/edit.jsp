<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 
    📌 edit.jsp
    - 기존 값 불러와서 수정
    - form → RestController(/api/boardnotice/edit) 호출
-->

<section class="max-w-4xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-6">

    <h1 class="text-3xl font-bold text-gray-800">✏️ 공지 수정</h1>

    <form action="/pulse/api/boardnotice/edit" method="post" class="space-y-5">

        <!-- PK -->
        <input type="hidden" name="boardSeq" value="${dto.boardSeq}">

        <!-- 작성자(읽기전용) -->
        <div>
            <label class="text-sm text-gray-600">작성자</label>
            <input type="text" name="writer" value="${dto.writer}" readonly
                   class="w-full mt-1 p-2 border rounded-lg bg-gray-100">
        </div>

        <!-- 제목 -->
        <div>
            <label class="text-sm text-gray-600">제목</label>
            <input type="text" name="title" value="${dto.title}"
                   class="w-full mt-1 p-2 border rounded-lg focus:outline-brand"
                   required>
        </div>

        <!-- 내용 -->
        <div>
            <label class="text-sm text-gray-600">내용</label>
            <textarea name="content" rows="10"
                      class="w-full mt-1 p-2 border rounded-lg focus:outline-brand"
                      required>${dto.content}</textarea>
        </div>

        <!-- 버튼 -->
        <div class="flex justify-end gap-3">
            <button onclick="location.href='/pulse/boardnotice/view.do?seq=${dto.boardSeq}'"
                    type="button"
                    class="px-4 py-2 bg-gray-200 rounded-lg hover:bg-gray-300 transition">
                취소
            </button>

            <button type="submit"
                    class="px-4 py-2 bg-brand text-white rounded-lg hover:bg-brand-dark transition">
                수정완료
            </button>
        </div>

    </form>
</section>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section class="max-w-4xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">

    <!-- 제목 -->
    <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <h1 class="text-3xl font-bold text-black">✏️ 새 게시글 작성</h1>
    </div>

    <!-- 설명 -->
    <p class="text-gray-600 text-sm">
        크루 게시판에 새로운 글을 작성합니다.
    </p>

    <!-- ✅ 폼 영역 -->
    <form id="boardForm" enctype="multipart/form-data" class="space-y-6">

        <!-- 서버로 전달 -->
        <input type="hidden" name="crewSeq" value="${crewSeq}"/>
        <!-- 닉네임은 DB 저장용이 아니라 표시용이지만, 필요 시 함께 전송 가능 -->
        <input type="hidden" name="nickname" value="${nickname}"/>
        <input type="hidden" name="accountId" value="${accountId}"/>

        <!-- 제목 -->
        <div>
            <label for="title" class="block flex flex-column justify-between text-gray-700 font-semibold mb-4">
                제목
                <div>
                    <p class="text-sm text-gray-500 mt-2 mb-2">
                        작성자: <span class="font-semibold text-brand">${nickname}</span>
                    </p>
                </div>
            </label>
            <input type="text" id="title" name="title" required
                   placeholder="게시글 제목을 입력하세요"
                   class="w-full rounded-lg border border-gray-300 focus:ring-2 focus:ring-brand focus:border-brand px-4 py-2 text-sm transition"/>
        </div>

        <!-- 내용 -->
        <div>
            <label for="content" class="block text-gray-700 font-semibold mb-2">내용</label>
            <textarea id="content" name="content" required rows="10"
                      placeholder="게시글 내용을 입력하세요"
                      class="w-full rounded-lg border border-gray-300 focus:ring-2 focus:ring-brand focus:border-brand px-4 py-3 text-sm transition resize-y"></textarea>
        </div>

        <!-- 첨부파일 -->
        <div>
            <label for="attach" class="block text-gray-700 font-semibold mb-2">파일 첨부</label>
            <input type="file" id="attach" name="attach"
                   class="block w-full text-sm text-gray-700 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none
              file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:bg-brand file:text-white hover:file:bg-brand-dark transition"/>
            <p class="text-xs text-gray-400 mt-1">* 이미지, 문서 등 최대 10MB까지 업로드 가능</p>
        </div>

        <!-- 버튼 -->
        <div class="flex justify-end gap-3 pt-4">
            <button type="button"
                    onclick="location.href='${pageContext.request.contextPath}/crewboard/list'"
                    class="px-5 py-2.5 rounded-lg bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold transition">
                취소
            </button>

            <button type="submit"
                    class="px-5 py-2.5 rounded-lg bg-brand text-white font-semibold hover:bg-brand-dark shadow-md hover:shadow-lg transition">
                작성 완료
            </button>
        </div>
    </form>
</section>

<!-- ✅ JS (REST API로 전송) -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section class="max-w-6xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">

    <div class="flex flex-col sm:flex-row justify-between items-center">
        <h1 class="text-3xl font-bold text-brand">📢 공지사항</h1>
    </div>

    <p class="text-gray-600 text-sm">전체 공지사항을 확인할 수 있습니다.</p>

    <div class="block overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-left border-collapse">
            <thead class="bg-gray-50 text-gray-600 text-sm uppercase font-semibold">
            <tr>
                <th class="px-6 py-3 text-center w-12">No</th>
                <th class="px-6 py-3">제목</th>
                <th class="px-6 py-3 w-32 text-center">작성자</th>
                <th class="px-6 py-3 w-36 text-center">작성일</th>
                <th class="px-6 py-3 w-24 text-center">조회수</th>
            </tr>
            </thead>
            <tbody id="board-body" class="text-gray-700 text-sm divide-y divide-gray-100">
                <tr><td colspan="5" class="text-center text-gray-400 py-6">불러오는 중...</td></tr>
            </tbody>
        </table>
    </div>

    <div class="flex justify-end">
        <a href="${pageContext.request.contextPath}/boardnotice/add.do"
           class="px-4 py-2 bg-brand text-white font-semibold rounded-lg hover:bg-brand-dark transition">
            ✏️ 새 글 작성
        </a>
    </div>
</section>

<script>
document.addEventListener("DOMContentLoaded", () => {

    fetch(`/pulse/api/boardnotice/list`)
        .then(res => res.json())
        .then(list => {
            const tbody = document.getElementById('board-body');
            tbody.innerHTML = "";

            list.forEach(item => {
                tbody.innerHTML += `
                    <tr class="hover:bg-gray-50 cursor-pointer"
                        onclick="location.href='/pulse/boardnotice/view.do?seq=${item.boardSeq}'">
                        <td class="px-6 py-3 text-center">${item.boardSeq}</td>
                        <td class="px-6 py-3">${item.title}</td>
                        <td class="px-6 py-3 text-center">${item.writer}</td>
                        <td class="px-6 py-3 text-center">${item.regdate}</td>
                        <td class="px-6 py-3 text-center">${item.readCount}</td>
                    </tr>
                `;
            });
        });

});
</script>

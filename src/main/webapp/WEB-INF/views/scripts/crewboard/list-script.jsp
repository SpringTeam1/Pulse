<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const crewSeq = "${crewSeq}";
        const tbody = document.getElementById("board-body");
        const pagebar = document.getElementById("pagebar");

        const pageSize = 10;
        let currentPage = 1;

        async function loadBoardList(page = 1) {
            tbody.innerHTML = `<tr><td colspan="6" class="text-center text-gray-400 py-6">불러오는 중...</td></tr>`;
            try {
                const res = await fetch(`/pulse/api/v1/crew/board/`+crewSeq+'?page='+page);
                const data = await res.json();
                const list = data.list;
                const totalCount = data.totalCount;
                const totalPages = Math.ceil(totalCount / pageSize);

                renderTable(list, page);
                renderPagebar(totalPages, page);
            } catch (err) {
                console.error("❌ 데이터 로드 실패:", err);
                tbody.innerHTML = `<tr><td colspan="6" class="text-center text-red-500 py-6">데이터를 불러오지 못했습니다.</td></tr>`;
            }
        }

        function renderTable(list, page) {
            tbody.innerHTML = "";
            if (!list || list.length === 0) {
                tbody.innerHTML = `<tr><td colspan="6" class="text-center text-gray-400 py-6">게시글이 없습니다.</td></tr>`;
                return;
            }

            list.forEach((board, i) => {
                const nickname = board.nickname ? board.nickname : "-";
                const regdate = board.regdate ? board.regdate : "-";
                const readCount = board.readCount ? board.readCount : 0;
                const favoriteCount = board.favoriteCount ? board.favoriteCount : 0;

                const row = `
          <tr class="hover:bg-gray-50 transition">
            <td class="px-6 py-3 text-center">\${(page - 1) * pageSize + (i + 1)}</td>
            <td class="text-center px-6 py-3">
              <a href="/pulse/crewboard/view?boardContentSeq=\${board.boardContentSeq}"
                 class="text-black hover:text-brand font-medium">
                \${board.title}
              </a>
            </td>
            <td class="px-6 py-3 text-center">\${nickname}</td>
            <td class="px-6 py-3 text-center">\${regdate}</td>
            <td class="px-6 py-3 text-center">\${readCount}</td>
            <td class="px-6 py-3 text-center">\${favoriteCount}</td>
          </tr>`;
                tbody.insertAdjacentHTML("beforeend", row);
            });
        }

        function renderPagebar(totalPages, current) {
            pagebar.innerHTML = "";
            if (totalPages <= 1) return;

            const createButton = (num, label = num) => {
                const btn = document.createElement("button");
                btn.textContent = label;
                btn.className =
                    "px-3 py-1 rounded-lg border text-sm " +
                    (num === current
                        ? "bg-brand text-white border-brand"
                        : "text-gray-600 hover:bg-gray-100");
                btn.onclick = () => loadBoardList(num);
                return btn;
            };

            if (current > 1) pagebar.appendChild(createButton(current - 1, "«"));
            for (let i = 1; i <= totalPages; i++) {
                pagebar.appendChild(createButton(i));
            }
            if (current < totalPages) pagebar.appendChild(createButton(current + 1, "»"));
        }

        loadBoardList(currentPage);
    });
</script>

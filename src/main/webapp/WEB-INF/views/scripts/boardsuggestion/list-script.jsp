<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!-- JS: 이후 AJAX 목록 업데이트, 정렬 버튼 등 추가 가능 -->
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script>
document.addEventListener('DOMContentLoaded', () => {

  const ctx = "${ctx}";

  const tbody = document.getElementById('board-body');
  const pagebar = document.getElementById('pagebar');
  const searchInput = document.getElementById('searchInput');
  const sortSelect = document.getElementById('sortSelect');

  let currentPage = 1;
  const pageSize = 10;

  // ===============================
  // ⭐ 목록 로드 함수
  // ===============================
  async function loadList(page = 1) {

    tbody.innerHTML = 
      '<tr><td colspan="6" class="text-center text-gray-400 py-6">불러오는 중...</td></tr>';

    const keyword = searchInput.value.trim();
    const sort = sortSelect.value;

    const apiUrl =
      ctx + "/boardsuggestion/api/list"
      + "?page=" + page
      + "&size=" + pageSize
      + "&keyword=" + encodeURIComponent(keyword)
      + "&sort=" + sort
      + "&order=desc";

    try {
      const res = await fetch(apiUrl);

      if (!res.ok) {
        throw new Error("REST API 호출 실패");
      }

      const data = await res.json();

      if (!data.list || data.list.length === 0) {
        tbody.innerHTML =
          '<tr><td colspan="6" class="text-center text-gray-400 py-6">게시글이 없습니다.</td></tr>';
        pagebar.innerHTML = "";
        return;
      }

      // ⭐ 테이블 렌더링
      let html = "";

      data.list.forEach(dto => {

        html += `
          <tr class="hover:bg-gray-50 transition">
            <td class="px-6 py-3 text-center">\${dto.boardContentSeq}</td>

            <td class="px-6 py-3 text-center">
              <a href="${ctx}/boardsuggestion/view?boardContentSeq=\${dto.boardContentSeq}"
                 class="hover:text-brand-dark font-medium">
                \${dto.title}
              </a>
            </td>

            <td class="px-6 py-3 text-center">\${dto.adto?.nickname ?? "-"}</td>

            <td class="px-6 py-3 text-center">\${dto.regdate ?? ""}</td>

            <td class="px-6 py-3 text-center">\${dto.readCount}</td>

            <td class="px-6 py-3 text-center">\${dto.favoriteCount}</td>
          </tr>
        `;
      });

      tbody.innerHTML = html;

      renderPagination(data.totalPages, page);

    } catch (e) {
      console.error("REST ERROR → ", e);

      tbody.innerHTML =
        '<tr><td colspan="6" class="text-center text-red-400 py-6">데이터를 불러오는 중 문제가 발생했습니다.</td></tr>';
    }
  }

  // ===============================
  // ⭐ 페이지네이션
  // ===============================
  function renderPagination(totalPages, current) {
    pagebar.innerHTML = "";

    if (totalPages <= 1) return;

    for (let i = 1; i <= totalPages; i++) {

      const btn = document.createElement("button");

      btn.textContent = i;

      btn.className =
        "px-3 py-1 text-sm rounded transition " +
        (i === current ? "bg-brand text-white" : "bg-gray-100 text-gray-600 hover:bg-gray-200");

      btn.addEventListener("click", () => {
        loadList(i);
      });

      pagebar.appendChild(btn);
    }
  }

  // ===============================
  // ⭐ 검색 엔터 입력
  // ===============================
  searchInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
      loadList(1);
    }
  });

  // ===============================
  // ⭐ 정렬 변경 즉시 반영
  // ===============================
  sortSelect.addEventListener("change", () => {
    loadList(1);
  });

  // ===============================
  // ⭐ 첫 로딩
  // ===============================
  loadList();

});
</script>
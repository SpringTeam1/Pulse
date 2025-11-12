<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- JS: 이후 AJAX 목록 업데이트, 정렬 버튼 등 추가 가능 -->
<script>
document.addEventListener('DOMContentLoaded', () => {
  const tbody = document.getElementById('board-body');
  const searchInput = document.getElementById('searchInput');
  const sortSelect = document.getElementById('sortSelect');
  const pagebar = document.getElementById('pagebar');

  let currentPage = 1;
  const pageSize = 10;

  async function loadList(page = 1) {
    tbody.innerHTML = `
      <tr>
        <td colspan="6" class="text-center text-gray-400 py-6">불러오는 중...</td>
      </tr>`;

    const keyword = searchInput.value.trim();
    const sort = sortSelect.value;

    try {
      const res = await fetch(`${pageContext.request.contextPath}/boardsuggestion/api/list?page=${page}&size=${pageSize}&keyword=${encodeURIComponent(keyword)}&sort=${sort}`);
      if (!res.ok) throw new Error('서버 오류');
      const data = await res.json();

      if (!data.list || data.list.length === 0) {
        tbody.innerHTML = `
          <tr><td colspan="6" class="text-center text-gray-400 py-6">게시글이 없습니다.</td></tr>`;
        pagebar.innerHTML = '';
        return;
      }

      tbody.innerHTML = data.list.map(dto => `
        <tr class="hover:bg-gray-50 transition">
          <td class="px-6 py-3 text-center">${dto.boardContentSeq}</td>
          <td class="px-6 py-3">
            <a href="${pageContext.request.contextPath}/boardsuggestion/view?boardContentSeq=${dto.boardContentSeq}"
               class="text-brand hover:text-brand-dark font-medium">${dto.title}</a>
          </td>
          <td class="px-6 py-3 text-center">${dto.adto?.name || '-'}</td>
          <td class="px-6 py-3 text-center">${dto.regdate || ''}</td>
          <td class="px-6 py-3 text-center">${dto.readCount}</td>
          <td class="px-6 py-3 text-center">${dto.favoriteCount ?? 0}</td>
        </tr>
      `).join('');

      renderPagination(data.totalPages, page);

    } catch (err) {
      console.error(err);
      tbody.innerHTML = `
        <tr><td colspan="6" class="text-center text-red-400 py-6">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>`;
    }
  }

  // 페이지네이션 렌더링
  function renderPagination(totalPages, current) {
    pagebar.innerHTML = '';

    if (totalPages <= 1) return;

    for (let i = 1; i <= totalPages; i++) {
      const btn = document.createElement('button');
      btn.textContent = i;
      btn.className = `px-3 py-1 rounded text-sm transition ${
        i === current
          ? 'bg-brand text-white'
          : 'bg-gray-100 hover:bg-gray-200 text-gray-600'
      }`;
      btn.addEventListener('click', () => {
        currentPage = i;
        loadList(currentPage);
      });
      pagebar.appendChild(btn);
    }
  }

  // 검색어 입력 시 Enter로 검색
  searchInput.addEventListener('keypress', e => {
    if (e.key === 'Enter') {
      currentPage = 1;
      loadList(currentPage);
    }
  });

  // 정렬 변경 시 즉시 반영
  sortSelect.addEventListener('change', () => {
    currentPage = 1;
    loadList(currentPage);
  });

  // 첫 로드
  loadList();
});
</script>
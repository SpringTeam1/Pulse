<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- JS: 이후 AJAX 목록 업데이트, 정렬 버튼 등 추가 가능 -->
<script>
document.addEventListener('DOMContentLoaded', async () => {
  const tbody = document.getElementById('board-body');

  try {
    const res = await fetch('${pageContext.request.contextPath}/boardsuggestion/api/list');
    if (!res.ok) throw new Error('서버 응답 오류');
    const list = await res.json();

    if (list.length === 0) {
      tbody.innerHTML = `
        <tr>
          <td colspan="6" class="text-center text-gray-400 py-6">게시글이 없습니다.</td>
        </tr>`;
      return;
    }

    // 테이블 내용 생성
    tbody.innerHTML = list.map(dto => `
      <tr class="hover:bg-gray-50 transition">
        <td class="px-6 py-3 text-center">${dto.boardContentSeq}</td>
        <td class="px-6 py-3">
          <a href="${pageContext.request.contextPath}/boardsuggestion/view?boardContentSeq=${dto.boardContentSeq}"
             class="text-brand hover:text-brand-dark font-medium">
            ${dto.title}
          </a>
        </td>
        <td class="px-6 py-3">${dto.adto ? dto.adto.name : '-'}</td>
        <td class="px-6 py-3">${dto.regdate || ''}</td>
        <td class="px-6 py-3 text-center">${dto.readCount}</td>
      </tr>
    `).join('');

  } catch (err) {
    console.error(err);
    tbody.innerHTML = `
      <tr>
        <td colspan="6" class="text-center text-red-400 py-6">
          데이터를 불러오는 중 오류가 발생했습니다.
        </td>
      </tr>`;
  }
});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- JS: 이후 AJAX 목록 업데이트, 정렬 버튼 등 추가 가능 -->
<script>
document.addEventListener("DOMContentLoaded", () => {

    const ctx = "${pageContext.request.contextPath}";
    const seq = "${dto.boardContentSeq}";

    // ===============================
    // 🗑 삭제 버튼 → 모달 열기
    // ===============================
    const delBtn = document.getElementById("btn-del");

    if (delBtn) {
        delBtn.addEventListener("click", () => {
            openDeleteModal();
        });
    }

    // ===============================
    // ❤️ 좋아요 기능 (그대로 유지)
    // ===============================
    const likeImg = document.getElementById("like-img");
    if (likeImg) {
        likeImg.addEventListener("click", async () => {
            try {
                const res = await fetch(`\${ctx}/api/v1/boardsuggestion/like/\${seq}`, {
                    method: "POST"
                });

                if (!res.ok) {
                    alert("좋아요 처리 실패");
                    return;
                }

                const data = await res.json();
                document.getElementById("like-count").textContent = "❤️ " + data.favoriteCount;

                likeImg.classList.add("scale-125");
                setTimeout(() => likeImg.classList.remove("scale-125"), 200);

            } catch (e) {
                console.error("LIKE ERROR →", e);
                alert("서버 오류가 발생했습니다.");
            }
        });
    }

    // ============================================
    // 🔥 삭제 모달 생성 (DOM 완전 동적 생성)
    // ============================================
    function openDeleteModal() {

        // 모달 HTML
        const modal = document.createElement("div");
        modal.id = "delete-modal";
        modal.className = "fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50";

        modal.innerHTML = `
            <div class="bg-white rounded-xl shadow-xl p-8 w-96 space-y-6 text-center">
                <h2 class="text-xl font-bold text-gray-900">정말 삭제하시겠습니까?</h2>
                <p class="text-gray-600 text-sm">삭제 후에는 되돌릴 수 없습니다.</p>

                <div class="flex gap-3 justify-center">
                    <button id="modal-confirm"
                        class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition">
                        삭제하기
                    </button>

                    <button id="modal-cancel"
                        class="px-4 py-2 bg-gray-200 rounded-lg hover:bg-gray-300 transition">
                        취소
                    </button>
                </div>
            </div>
        `;

        // body에 부착
        document.body.appendChild(modal);

        // “취소” 버튼
        modal.querySelector("#modal-cancel").addEventListener("click", () => {
            modal.remove();
        });

        // “삭제하기” 버튼 → REST 요청
        modal.querySelector("#modal-confirm").addEventListener("click", async () => {
            try {
                const res = await fetch(`\${ctx}/api/v1/boardsuggestion/delete/\${seq}`, {
                    method: "DELETE"
                });

                if (!res.ok) {
                    alert("삭제 중 오류 발생");
                    return;
                }

                alert("게시글이 삭제되었습니다.");
                location.href = `\${ctx}/boardsuggestion/list`;

            } catch (e) {
                console.error("DELETE ERROR →", e);
                alert("삭제 처리 실패");
            }
        });
    }

});
</script>

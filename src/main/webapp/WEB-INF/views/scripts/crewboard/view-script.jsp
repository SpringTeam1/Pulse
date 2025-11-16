<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        console.log("📄 crewboard.view.jsp loaded:", "${board.title}");

        const likeImg = document.getElementById("like-img");
        const delBtn = document.getElementById("btn-del");

        /** ❤️ 좋아요 처리 */
        likeImg?.addEventListener("click", async (e) => {
            const img = e.currentTarget;
            const boardContentSeq = img.dataset.board;

            if (img.dataset.liked === "true") return;

            try {
                const res = await fetch(`${pageContext.request.contextPath}/api/v1/crew/board/\${boardContentSeq}/like`, {
                    method: "POST",
                });
                const data = await res.json();

                if (res.ok && data.success) {
                    img.style.filter =
                        "invert(30%) sepia(100%) saturate(6000%) hue-rotate(-10deg) brightness(1.1)";
                    img.dataset.liked = "true";
                    document.getElementById("like-count").textContent = `❤️ \${data.favoriteCount}`;
                } else {
                    alert(`❌ ${data.message || "좋아요 실패"}`);
                }
            } catch (err) {
                console.error(err);
                alert("⚠️ 서버 오류가 발생했습니다.");
            }
        });

        /** 🗑️ 게시글 삭제 */
        delBtn?.addEventListener("click", async (e) => {
            if (!confirm("정말 삭제하시겠습니까?")) return;

            const seq = e.target.dataset.seq;

            try {
                const res = await fetch(`${pageContext.request.contextPath}/api/v1/crew/board/\${seq}/del`, {
                    method: "DELETE",
                });
                const result = await res.json();

                // 성공
                if (res.ok && result.success) {
                    alert(result.message);
                    location.href = `${pageContext.request.contextPath}/crewboard/list`;
                    return;
                }

                // 실패 — 상태코드별 처리
                switch (res.status) {
                    case 401:
                        alert("로그인이 필요합니다.");
                        location.href = `${pageContext.request.contextPath}/customlogin`;
                        break;
                    case 403:
                        alert("본인 글만 삭제할 수 있습니다.");
                        location.href = `${pageContext.request.contextPath}/crewboard/view?boardContentSeq=${seq}`;
                        break;
                    case 404:
                        alert("존재하지 않는 게시글입니다.");
                        location.href = `${pageContext.request.contextPath}/crewboard/list`;
                        break;
                    default:
                        alert(result.message || "삭제 중 오류가 발생했습니다.");
                        location.href = `${pageContext.request.contextPath}/crewboard/list`;
                }
            } catch (err) {
                console.error("❌ 삭제 중 오류:", err);
                alert("⚠️ 서버 오류가 발생했습니다.");
                location.href = `${pageContext.request.contextPath}/crewboard/list`;
            }
        });
    });

    const commentBtn = document.getElementById("btn-comment");

    commentBtn?.addEventListener("click", async (e) => {

        const content = document.getElementById("comment-content").value;
        const boardSeq = ${board.boardContentSeq};

        if (!content.trim()) {
            alert("댓글 내용을 입력하세요")
            return;
        }

        await fetch(`/pulse/api/v1/crew/board/comment/` + boardSeq, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                content: content,
            })
        });

        location.reload();

    })

    document.addEventListener("DOMContentLoaded", () => {

        const boardContentSeq = "${board.boardContentSeq}";
        const commentBox = document.getElementById("comment-list");

        async function loadCommentList() {


            commentBox.innerHTML = `
            <tr><td colspan="6" class="text-center text-gray-400 py-6">불러오는 중...</td></tr>
        `;

            try {

                const res = await fetch(`/pulse/api/v1/crew/board/comment/\${boardContentSeq}`);
                const list = await res.json();

                renderTable(list);

            } catch (err) {
                console.error("❌ 데이터 로드 실패:", err);
                commentBox.innerHTML = `
                <tr><td colspan="6" class="text-center text-red-500 py-6">데이터를 불러오지 못했습니다.</td></tr>
            `;
            }
        }

        function renderTable(list) {

            commentBox.innerHTML = "";

            if (list.length === 0) {
                commentBox.innerHTML = `
                <tr><td colspan="6" class="text-center text-gray-400 py-6">댓글이 없습니다.</td></tr>
            `;
                return;
            }

            list.forEach((c, i) => {
                const nickname = c.nickname ?? "-";
                const content  = c.content ?? "-";

                const row =  `
        <div class="flex gap-3 p-4 bg-white rounded-xl shadow-sm border border-gray-100 hover:shadow transition cursor-default">

            <c:choose>
                <c:when test="\${profilePhoto ne null && profilePhoto ne 'pic.png'}">
                    <img src="/pulse/asset/pic/\${profilePhoto}"
                         class="w-10 h-10 rounded-full object-cover" />
                </c:when>

                <c:otherwise>
                    <div class="w-10 h-10 flex items-center justify-center rounded-full bg-gray-200 font-semibold text-gray-600">
                        \${nickname.charAt(0).toUpperCase()}
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="flex-1">
                <div class="flex items-center gap-2">
                    <span class="font-semibold text-gray-900">\${nickname}</span>
                    <span class="text-xs text-gray-400">\${c.regdate ?? ""}</span>
                </div>

                <div class="mt-2 bg-gray-50 p-3 rounded-xl text-gray-800 leading-relaxed">
                   \${content}
                </div>
            </div>
        </div>
    `;

                commentBox.insertAdjacentHTML("beforeend", row);
            });
        }

        loadCommentList();
    });

</script>
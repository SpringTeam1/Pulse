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
                        location.href = `${pageContext.request.contextPath}/test-login`;
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
</script>


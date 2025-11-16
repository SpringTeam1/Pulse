<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        console.log("📝 crewboardadd.jsp loaded, nickname:", "${nickname}");

        const form = document.getElementById("boardForm");

        form.addEventListener("submit", async (e) => {
            e.preventDefault();

            const formData = new FormData(form);

            try {
                const res = await fetch("/pulse/api/v1/crew/board", {
                    method: "POST",
                    body: formData
                });

                const result = await res.json();
                alert(result.message);

                if (result.success) {
                    location.href = "${pageContext.request.contextPath}/crewboard/list";
                }
            } catch (err) {
                console.error("❌ 게시글 등록 실패:", err);
                alert("서버 오류가 발생했습니다.");
            }
        });
    });
</script>

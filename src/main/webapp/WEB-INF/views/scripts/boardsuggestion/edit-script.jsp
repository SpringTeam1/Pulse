<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- JS: 이후 AJAX 목록 업데이트, 정렬 버튼 등 추가 가능 -->
<script>
document.addEventListener("DOMContentLoaded", () => {

    const ctx = "${pageContext.request.contextPath}";
    const seq = "${dto.boardContentSeq}";
    const form = document.getElementById("editForm");

    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        if (!confirm("수정 내용을 저장하시겠습니까?")) {
            return;
        }

        /* const url = `\${ctx}/api/v1/boardsuggestion/edit/\${seq}`; */
        const formData = new FormData(form);

        try {
            const res = await fetch(\${ctx}/api/v1/boardsuggestion/edit/\${seq}`, {
                method: "POST",
                body: formData
            });

            if (!res.ok) {
                alert("수정 중 오류가 발생했습니다.");
                return;
            }

            const data = await res.json();

            if (data.status === "OK") {
                // 서버에서 준 redirectUrl은 contextPath를 붙여서 이동
                const redirectUrl = data.redirectUrl || `/boardsuggestion/view?boardContentSeq=\${seq}`;
                alert("게시글이 수정되었습니다.");
                location.href = ctx + redirectUrl;
            } else if (data.status === "NOT_FOUND") {
                alert("게시글을 찾을 수 없습니다.");
            } else {
                alert("수정에 실패했습니다.");
            }

        } catch (err) {
            console.error("EDIT ERROR →", err);
            alert("서버 통신 중 오류가 발생했습니다.");
        }
    });

});
</script>

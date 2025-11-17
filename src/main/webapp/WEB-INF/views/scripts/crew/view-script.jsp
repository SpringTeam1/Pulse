<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script>
    async function joinCrew(crewSeq) {
        if (!confirm("이 크루에 가입 신청하시겠습니까?")) return;

        try {
            const res = await fetch(`/pulse/api/v1/crewjoin/`+ crewSeq, {
                method: "POST",
            });

            const data = await res.json();

            // 응답 확인
            alert(data.message);

            if (data.success) {
                // 성공 시 즉시 새로고침 or 가입상태 갱신
                location.reload();
            }

        } catch (err) {
            console.error("❌ 가입 신청 중 오류:", err);
            alert("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }
</script>
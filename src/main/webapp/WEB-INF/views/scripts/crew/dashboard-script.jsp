<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

    document.addEventListener("DOMContentLoaded", initDashBoard)

    async function initDashBoard() {
        const crewSeq = "${crewSeq}";

        // 1) 총 게시글 수
        loadTotalPostCount(crewSeq);
        // 2) 이번주 게시글 수
        loadWeeklyTop2count(crewSeq);

        loadTopViewPost(crewSeq);

    }

    async function loadTotalPostCount(crewSeq) {
        try{
            const rs = await fetch(`/pulse/api/v1/crew/board/boardcount/\${crewSeq}`);
            const data = await rs.json();

            document.getElementById("post-total").innerText = data;
        }catch(err){
            console.error("❌ 총 게시글 수 가져오기 실패:", err);
            document.getElementById("post-total").innerText = "-";
        }

    }

    async function loadWeeklyTop2count(crewSeq) {
        try{
            const rs = await fetch(`/pulse/api/v1/crew/board/boardtop2count/\${crewSeq}`);
            const data = await rs.json();

            document.getElementById("post-weekly").innerText = data;

        }catch(err){
            console.error("❌ TOP2 게시글 가져오기 실패:", err);
            document.getElementById("post-weekly").innerText = "-";
        }
    }


    async function loadTopViewPost(crewSeq) {
        try {
            const res = await fetch(`/pulse/api/v1/crew/board/boardtop2/\${crewSeq}`);
            const dto = await res.json();

            if (!dto) return;

            document.getElementById("post-topview-title").innerText = dto.title || "-";
            document.getElementById("post-topview-writer").innerText = dto.nickname || "익명";
            document.getElementById("post-topview-count").innerText = dto.readCount || 0;

        } catch (err) {
            console.error("❌ 조회수 Top 가져오기 실패:", err);
        }
    }

</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

    document.addEventListener("DOMContentLoaded", initDashBoard)

    async function initDashBoard() {
        const crewSeq = "${crewSeq}";

        // 1) 총 게시글 수
        loadTotalPostCount(crewSeq);

        // 2) 이번주 TOP2 게시글
        loadWeeklyTop2(crewSeq);

    }

    async function loadTotalPostCount(crewSeq) {
        try{
            const rs = await fetch(`/pulse/api/v1/crew/board/boardcount/\${crewSeq}`);
            const data = await rs.json();

            document.getElementById("post-total");
        }catch(e){
            console.error("❌ 총 게시글 수 가져오기 실패:", err);
            document.getElementById("post-total").innerText = "-";
        }

    }

    async function loadWeeklyTop2(crewSeq) {
        try{
            const rs = await fetch(`/pulse/api/v1/crew/board/boardtop2count/\${crewSeq}`);
            const data = await rs.json();

            renderTop2(data);

        }catch(e){
            console.error("❌ TOP2 게시글 가져오기 실패:", err);
            document.getElementById("post-weekly").innerText = "-";
        }
    }

</script>
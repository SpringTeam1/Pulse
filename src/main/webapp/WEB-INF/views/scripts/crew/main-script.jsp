<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    // 📍 현재 위치로 새로고침
    document.getElementById("location-btn").addEventListener("click", () => {
        if (!navigator.geolocation) {
            alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
            return;
        }
        navigator.geolocation.getCurrentPosition(
            pos => {
                const lat = pos.coords.latitude;
                const lng = pos.coords.longitude;
                window.location.href = '${pageContext.request.contextPath}/crewmain?lat=' + lat + '&lng=' + lng;
            },
            err => {
                console.error("위치 정보 오류:", err.message);
                alert("위치 정보를 가져오지 못했습니다.");
            }
        );
    });

    // 🧭 카드 클릭 시 상세보기로 이동
    document.querySelectorAll(".crew-card").forEach(card => {
        card.addEventListener("click", () => {
            const seq = card.dataset.crewSeq;
            if (seq) {
                window.location.href = '${pageContext.request.contextPath}/crewview?crewSeq=' + seq;
            }
        });
    });
</script>
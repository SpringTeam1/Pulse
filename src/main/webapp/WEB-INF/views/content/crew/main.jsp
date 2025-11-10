<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- ===================================================================
✅ crew.main.jsp
- Spring Legacy + JSTL + Tailwind 통합 버전
- fetch나 Ajax 없음 (기존처럼 서버 렌더링 방식)
- 위치 갱신 버튼은 페이지 새로고침 기반
=================================================================== -->

<section class="max-w-6xl mx-auto px-4 py-8 space-y-10">

    <!-- 🎯 내 주변 크루 -->
    <div class="bg-white rounded-xl shadow p-6">
        <div class="flex items-center justify-between mb-6">
            <h2 class="text-3xl font-bold text-black">내 주변 크루</h2>
            <button id="location-btn"
                    class="text-2xl hover:text-brand-dark focus:outline-none transition"
                    title="현재 위치로 갱신">📍</button>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-6">
            <c:choose>
                <c:when test="${empty nearbyCrewList}">
                    <p class="text-gray-500 text-center col-span-full">근처에 등록된 크루가 없습니다.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="crew" items="${nearbyCrewList}">
                        <div class="crew-card bg-white rounded-xl border border-gray-200 shadow-sm hover:shadow-lg hover:-translate-y-1 transition overflow-hidden">
                            <div class="w-full h-40 bg-gray-100 flex items-center justify-center">
                                <img src="/pulse/crewmainFile/${empty crew.crewAttach ? 'default.jpg' : crew.crewAttach}"
                                     alt="${empty crew.crewName ? '크루 이미지' : crew.crewName}"
                                     class="w-full h-full object-cover rounded-t-lg"
                                     onerror="this.onerror=null; this.style.display='none'; this.parentElement.innerHTML='🏃';" />
                            </div>
                            <div class="p-4 flex flex-col gap-1">
                                <h3 class="text-lg font-semibold text-gray-800">
                                        ${empty crew.crewName ? '이름 없음' : crew.crewName}
                                </h3>
                                <p class="text-sm text-gray-600">
                                    <strong class="text-brand-dark">지역:</strong>
                                        ${empty crew.regionCity ? '' : crew.regionCity}
                                        ${empty crew.regionCounty ? '' : crew.regionCounty}
                                </p>
                                <p class="text-sm text-gray-600">
                                    <strong class="text-brand-dark">인원:</strong>
                                        ${crew.memberCount ne null ? crew.memberCount : 0}명
                                </p>
                                <p class="text-sm text-gray-600">
                                    <strong class="text-brand-dark">크루장:</strong>
                                        ${empty crew.nickname ? '정보 없음' : crew.nickname}
                                </p>
                                <p class="text-sm text-gray-600">
                                    <strong class="text-brand-dark">거리:</strong>
                                    <fmt:formatNumber value="${crew.distance ne null ? crew.distance : 0}" pattern="0.00" /> km
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 🎯 인기 있는 크루 -->
    <div class="bg-white rounded-xl shadow p-6">
        <h2 class="text-3xl font-bold text-black mb-6">인기 있는 크루</h2>
        <div class="flex flex-col gap-6">
            <c:choose>
                <c:when test="${empty popularCrewList}">
                    <p class="text-gray-500 text-center">인기 크루가 없습니다.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="crew" items="${popularCrewList}">
                        <div class="flex flex-col sm:flex-row bg-white rounded-xl border border-gray-200 shadow-sm hover:shadow-lg hover:-translate-y-1 transition overflow-hidden">
                            <div class="w-full sm:w-48 h-36 bg-gray-100 flex-shrink-0 flex items-center justify-center">
                                <img src="/pulse/crewmainFile/${empty crew.crewAttach ? 'default.jpg' : crew.crewAttach}"
                                     alt="${empty crew.crewName ? '크루 이미지' : crew.crewName}"
                                     class="w-full h-full object-cover"
                                     onerror="this.onerror=null; this.style.display='none'; this.parentElement.innerHTML='🏃';" />
                            </div>
                            <div class="p-4 flex flex-col justify-between flex-1">
                                <h3 class="text-lg font-semibold text-gray-800 mb-2">
                                        ${empty crew.crewName ? '이름 없음' : crew.crewName}
                                </h3>
                                <div class="space-y-1 text-sm text-gray-600">
                                    <p><strong class="text-brand-dark">인원:</strong> ${crew.memberCount ne null ? crew.memberCount : 0}명</p>
                                    <p><strong class="text-brand-dark">크루장:</strong> ${empty crew.nickname ? '정보 없음' : crew.nickname}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 🎯 크루 생성 배너 -->
    <div class="bg-gray-100 rounded-xl text-center py-10 px-6">
        <p class="text-gray-700 text-lg mb-4">
            가입하고 싶은 크루가 없으신가요? 나만의 크루를 만들어보세요!
        </p>
        <a href="/pulse/crewregister"
           class="inline-block bg-brand text-white font-semibold px-6 py-3 rounded-full hover:bg-brand-dark transition">
            ✨ 크루 만들기
        </a>
    </div>

</section>

<!-- ✅ JS: 위치 기반 새로고침 -->
<script>
    document.getElementById("location-btn").addEventListener("click", () => {
        if (!navigator.geolocation) {
            alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
            return;
        }
        navigator.geolocation.getCurrentPosition(
            pos => {
                const lat = pos.coords.latitude;
                const lng = pos.coords.longitude;
                window.location.href = '/pulse/crewmain?lat=' + lat + '&lng=' + lng;
            },
            err => {
                console.error("위치 정보 오류:", err.message);
                alert("위치 정보를 가져오지 못했습니다.");
            }
        );
    });
</script>

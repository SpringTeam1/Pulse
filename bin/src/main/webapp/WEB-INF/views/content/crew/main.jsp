<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><section class="max-w-6xl mx-auto px-4 py-8 space-y-10">

    <!-- 🎯 내 주변 크루 -->
    <div class="bg-white rounded-xl shadow p-6">
        <div class="flex items-center justify-between mb-6">
            <h2 class="text-3xl font-bold text-black">내 주변 크루</h2>
            <button name="location-check-btn"
                    class="text-2xl hover:text-brand-dark focus:outline-none transition"
                    title="현재 위치로 갱신">📍</button>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-6 nearby-grid">
            <c:forEach items="${nearbyCrewList}" var="crew">
                <a href="/alldayrun/crewview.do?crewSeq=${crew.crewSeq}"
                   class="block bg-white rounded-xl border border-gray-200 shadow-sm hover:shadow-lg hover:-translate-y-1 transition overflow-hidden">
                    <div class="w-full h-40 bg-gray-100">
                        <img src="/alldayrun/crewmainFile/${crew.crewAttach}"
                             alt="${crew.crewName}"
                             class="w-full h-full object-cover rounded-t-lg" />
                    </div>
                    <div class="p-4 flex flex-col gap-1">
                        <h3 class="text-lg font-semibold text-gray-800">${crew.crewName}</h3>
                        <p class="text-sm text-gray-600"><strong class="text-brand-dark">지역:</strong> ${crew.regionCity} ${crew.regionCounty}</p>
                        <p class="text-sm text-gray-600"><strong class="text-brand-dark">인원:</strong> ${crew.memberCount}명</p>
                        <p class="text-sm text-gray-600"><strong class="text-brand-dark">크루장:</strong> ${crew.nickname}</p>
                        <p class="text-sm text-gray-600"><strong class="text-brand-dark">거리:</strong> ${crew.distance}</p>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>

    <!-- 🎯 인기 있는 크루 -->
    <div class="bg-white rounded-xl shadow p-6">
        <h2 class="text-3xl font-bold text-black mb-6">인기 있는 크루</h2>
        <div class="flex flex-col gap-6 popular-list">
            <c:forEach items="${popularCrewList}" var="crew">
                <a href="/alldayrun/crewview.do?crewSeq=${crew.crewSeq}"
                   class="flex flex-col sm:flex-row bg-white rounded-xl border border-gray-200 shadow-sm hover:shadow-lg hover:-translate-y-1 transition overflow-hidden">
                    <div class="w-full sm:w-48 h-36 bg-gray-100 flex-shrink-0">
                        <img src="/alldayrun/crewmainFile/${crew.crewAttach}"
                             alt="${crew.crewName}"
                             class="w-full h-full object-cover">
                    </div>
                    <div class="p-4 flex flex-col justify-between flex-1">
                        <h3 class="text-lg font-semibold text-gray-800 mb-2">${crew.crewName}</h3>
                        <div class="space-y-1 text-sm text-gray-600">
                            <p><strong class="text-brand-dark">인원:</strong> ${crew.memberCount}명</p>
                            <p><strong class="text-brand-dark">크루장:</strong> ${crew.nickname}</p>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>

    <!-- 🎯 크루 생성 배너 -->
    <div class="bg-gray-100 rounded-xl text-center py-10 px-6">
        <p class="text-gray-700 text-lg mb-4">가입하고 싶은 크루가 없으신가요? 나만의 크루를 만들어보세요!</p>
        <a href="/pulse/crewregister"
           class="inline-block bg-brand text-white font-semibold px-6 py-3 rounded-full hover:bg-brand-dark transition">
            ✨ 크루 만들기
        </a>
    </div>

</section>

<!-- ✅ 페이지별 JS -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const btn = document.querySelector('[name="location-check-btn"]');
        if (!btn) return;

        btn.addEventListener("click", () => {
            if (!navigator.geolocation) {
                alert("이 브라우저는 위치 정보 기능을 지원하지 않습니다.");
                return;
            }

            navigator.geolocation.getCurrentPosition(
                async (pos) => {
                    const { latitude, longitude } = pos.coords;
                    alert("주변 크루 목록을 갱신합니다.");
                    console.log("위도:", latitude, "경도:", longitude);

                    try {
                        const response = await fetch(`/pulse/crewmain.do?lat=${latitude}&lng=${longitude}`);
                        const html = await response.text();
                        const parser = new DOMParser();
                        const doc = parser.parseFromString(html, "text/html");
                        const newNearby = doc.querySelector(".nearby-grid");
                        if (newNearby) {
                            document.querySelector(".nearby-grid").innerHTML = newNearby.innerHTML;
                            console.log("✅ 주변 크루 목록 갱신 완료");
                        }
                    } catch (err) {
                        console.error("크루 목록 갱신 오류:", err);
                        alert("목록을 갱신하는 데 실패했습니다.");
                    }
                },
                (err) => {
                    console.error(err);
                    alert("위치 정보를 가져오는 데 실패했습니다.");
                }
            );
        });
    });
</script>

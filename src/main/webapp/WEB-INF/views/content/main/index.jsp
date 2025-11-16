<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section
	class="max-w-6xl mx-auto mt-8 p-6 lg:p-10 bg-gray-50 rounded-3xl shadow-2xl space-y-10">

	<div class="border-b-4 border-brand-light pb-4">
		<h1
			class="text-4xl font-extrabold text-gray-800 flex items-center gap-3">
			🔥 오늘의 러닝 컨디션 분석</h1>
		<p class="text-lg text-gray-600 mt-2">현재 위치 기반 날씨를 확인하고, 최적의 러닝
			계획을 세우세요!</p>

		<div id="running-guide"
			class="mt-4 p-3 rounded-xl bg-green-100 border border-green-300 text-green-800 font-semibold flex items-center gap-2">
			<span class="text-2xl">☀️</span> <span>현재 기온과 습도는 러닝하기 매우 좋은
				상태입니다!</span>
		</div>
	</div>

	<div
		class="bg-white border-l-4 border-brand-dark shadow-md rounded-lg p-4 flex items-center justify-between">
		<span class="text-xl font-bold text-gray-700">📍 현재 위치:</span> <span
			id="location"
			class="text-xl text-brand-dark font-extrabold animate-pulse">위치를
			불러오는 중...</span>
	</div>

	<div class="grid grid-cols-2 md:grid-cols-5 gap-4 md:gap-6 mt-6">

		<div
			class="col-span-2 md:col-span-1 bg-brand-light/20 rounded-2xl p-6 text-center shadow-lg hover:scale-105 transition duration-300">
			<h3 class="text-xl font-bold text-gray-700 mb-2">🌡️ 기온</h3>
			<p class="text-5xl font-extrabold text-brand-dark">
				<span id="temp">-</span>℃
			</p>
		</div>

		<div
			class="bg-blue-50 rounded-2xl p-6 text-center shadow hover:shadow-xl transition">
			<h3 class="text-lg font-semibold text-gray-600 mb-2">💧 습도</h3>
			<p class="text-3xl font-bold text-blue-600">
				<span id="humidity">-</span>%
			</p>
		</div>

		<div
			class="bg-yellow-50 rounded-2xl p-6 text-center shadow hover:shadow-xl transition">
			<h3 class="text-lg font-semibold text-gray-600 mb-2">☔ 강수확률</h3>
			<p class="text-3xl font-bold text-yellow-600">
				<span id="rainChance">-</span>%
			</p>
		</div>

		<div
			class="bg-indigo-50 rounded-2xl p-6 text-center shadow hover:shadow-xl transition">
			<h3 class="text-lg font-semibold text-gray-600 mb-2">🌤️ 날씨</h3>
			<p class="text-xl font-extrabold text-indigo-700 mt-2">
				<span id="skyCondition">-</span>
			</p>
		</div>

		<div
			class="bg-gray-100 rounded-2xl p-6 text-center shadow hover:shadow-xl transition">
			<h3 class="text-lg font-semibold text-gray-600 mb-2">🌧️ 강수형태</h3>
			<p class="text-xl font-extrabold text-gray-700 mt-2">
				<span id="precipitationType">-</span>
			</p>
		</div>
	</div>

	<div class="text-center pt-4">
		<!-- 해당하는 도메인 입력하기 -->
		<a href="${pageContext.request.contextPath}/record"
			class="inline-block px-12 py-4 bg-brand-dark text-white text-xl font-bold rounded-full shadow-lg hover:bg-brand transition duration-300 transform hover:scale-105">
			🏃‍♂️ 오늘의 러닝 기록하기 </a>
	</div>

</section>

<section class="max-w-6xl mx-auto mt-12 p-6 lg:p-10 space-y-8">
	<div class="flex justify-between items-center border-b pb-3">
		<h2 class="text-3xl font-bold text-gray-800 flex items-center gap-2">
			📍 인기 러닝 코스</h2>
		<a href="${pageContext.request.contextPath}/course/coursemain.do"
			class="text-brand-dark font-medium hover:text-brand transition">전체 코스 보기 →</a>
	</div>

	<div class="grid md:grid-cols-3 gap-6">
		
		<c:forEach var="course" items="${popularCourses}" varStatus="status">
		<div class="bg-white rounded-xl shadow-lg overflow-hidden hover:shadow-2xl transition duration-300">
			<img src="https://picsum.photos/400/200?course=${course.courseSeq}" alt="${course.courseName}"
				class="w-full h-40 object-cover">
			<div class="p-4">
				<h3 class="text-xl font-bold text-gray-800">🌳 ${course.courseName}</h3>
				<p class="text-gray-600 text-sm mt-1">
					거리: ${course.courseLength}km | 시작: ${course.startAddress}
				</p>
			</div>
		</div>
		</c:forEach>
	</div>
</section>


<!-- 🌍 스크립트 -->
<script>
document.addEventListener("DOMContentLoaded", () => {
  const contextPath = '<%=request.getContextPath()%>';

  if (!navigator.geolocation) {
    alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
    return;
  }

  const runningGuide = document.getElementById("running-guide");

  navigator.geolocation.getCurrentPosition(
    // (이전과 동일한 Geolocation 및 Geocoding 로직은 생략하고 API 호출 및 날씨 가이드 로직에 집중)
    (pos) => {
      const lat = pos.coords.latitude.toFixed(6);
      const lon = pos.coords.longitude.toFixed(6);
      // ... (주소 불러오는 기존 로직)

      const apiUrl = contextPath + "/api/main?lat=" + lat + "&lon=" + lon;

      fetch(apiUrl)
        .then((res) => {
          if (!res.ok) throw new Error("서버 오류: " + res.status);
          return res.json();
        })
        .then((data) => {
          // 1. 기존 데이터 바인딩
          document.getElementById("temp").textContent = data.temp ?? "-";      
          document.getElementById("humidity").textContent = data.humidity ?? "-";
          document.getElementById("rainChance").textContent = data.rainChance ?? "-";
          document.getElementById("skyCondition").textContent = data.skyCondition ?? "-";
          document.getElementById("precipitationType").textContent = data.precipitationType ?? "-";
          
       	  // 2. ⭐ 러닝 가이드라인 업데이트 로직 (수정된 부분) ⭐
          const temp = data.temp ? parseFloat(data.temp) : null;
          const rainChance = data.rainChance ? parseInt(data.rainChance) : 0;
          const precipitationType = data.precipitationType || '없음';
          
          // Tailwind 클래스를 변경할 기본 패턴
          const baseReplacePattern = /bg-.*-100|border-.*-300|text-.*-800/g;
          
          if (temp === null) {
              runningGuide.innerHTML = '<span class="text-2xl">⚠️</span><span>날씨 정보를 불러올 수 없어 가이드라인 제공이 어렵습니다.</span>';
              runningGuide.className = runningGuide.className.replace(baseReplacePattern, 'bg-yellow-100 border-yellow-300 text-yellow-800');
          } 
          // 🚨 위험 조건 (극한 기온)
          else if (temp >= 28 || temp <= 0) {
              runningGuide.innerHTML = '<span class="text-2xl">🚨</span><span>극한 기온입니다! 러닝 대신 실내 운동을 강력히 권장합니다.</span>';
              runningGuide.className = runningGuide.className.replace(baseReplacePattern, 'bg-red-100 border-red-300 text-red-800');
          } 
          // 🌧️ 강수 형태 감지 (실제로 비가 오거나 옴)
          else if (precipitationType !== '없음' && precipitationType !== '-') {
              runningGuide.innerHTML = '<span class="text-2xl">🌧️</span><span>현재 강수가 감지되었습니다. 실내 운동을 고려하거나 방수 장비를 갖추세요.</span>';
              runningGuide.className = runningGuide.className.replace(baseReplacePattern, 'bg-blue-100 border-blue-300 text-blue-800');
          }
          // ☔ 높은 강수 확률 (비 올 가능성이 높음 - 40% 이상)
          else if (rainChance > 40) {
              runningGuide.innerHTML = '<span class="text-2xl">☔</span><span>강수 확률이 40% 이상입니다. 우산을 준비하고 일정을 조정하세요.</span>';
              runningGuide.className = runningGuide.className.replace(baseReplacePattern, 'bg-blue-100 border-blue-300 text-blue-800');
          } 
          // ☀️ 최적의 러닝 온도 (강수 확률이 낮고 온도 적합)
          else if (temp >= 10 && temp <= 22) {
              runningGuide.innerHTML = '<span class="text-2xl">☀️</span><span>현재 기온은 러닝하기 최적의 상태입니다! 즐거운 러닝하세요.</span>';
              runningGuide.className = runningGuide.className.replace(baseReplacePattern, 'bg-green-100 border-green-300 text-green-800');
          } 
          // 👌 기타 러닝 가능 날씨 (큰 위험 요소 없음)
          else {
              runningGuide.innerHTML = '<span class="text-2xl">👌</span><span>러닝 가능한 날씨입니다. 가볍게 몸을 풀고 시작하세요.</span>';
              runningGuide.className = runningGuide.className.replace(baseReplacePattern, 'bg-green-100 border-green-300 text-green-800');
          }
        })
        .catch((err) => {
          console.error("🚨 날씨 API 오류:", err);
          // 에러 시 가이드라인도 에러 메시지로 변경
          runningGuide.innerHTML = '<span class="text-2xl">❌</span><span>날씨 API 호출에 실패했습니다. 잠시 후 다시 시도해 주세요.</span>';
          runningGuide.className = runningGuide.className.replace(/bg-.*-100|border-.*-300|text-.*-800/g, 'bg-yellow-100 border-yellow-300 text-yellow-800');
        });
    },
    (err) => {
      // ... (Geolocation 오류 시 기존 로직)
    }
  );
});
</script>

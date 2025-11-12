<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="max-w-5xl mx-auto mt-10 p-8 bg-white shadow-lg rounded-2xl space-y-8">

  <!-- 🏃‍♂️ 제목 -->
  <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 border-b pb-4">
    <h1 class="text-3xl font-bold text-brand flex items-center gap-2">
      🏃‍♂️ 오늘의 러닝 컨디션
    </h1>
    <p class="text-gray-500 text-sm sm:text-base">러닝 전 날씨를 확인하세요</p>
  </div>

  <!-- 📍 현재 위치 -->
  <div class="bg-gray-50 border rounded-xl p-4 flex items-center gap-3">
    <span class="text-lg font-semibold text-gray-600">📍 현재 위치:</span>
    <span id="location" class="text-lg text-brand-dark font-medium">위치를 불러오는 중...</span>
  </div>

  <!-- 🌦️ 현재 날씨 카드 -->
  <div class="grid grid-cols-2 md:grid-cols-3 gap-6 mt-6">
    <div class="bg-brand/10 rounded-xl p-6 text-center shadow hover:shadow-md transition">
      <h3 class="text-gray-600 font-medium mb-2">기온</h3>
      <p class="text-3xl font-bold text-brand"><span id="temp">-</span>℃</p>
    </div>
    <div class="bg-blue-50 rounded-xl p-6 text-center shadow hover:shadow-md transition">
      <h3 class="text-gray-600 font-medium mb-2">습도</h3>
      <p class="text-3xl font-bold text-blue-600"><span id="humidity">-</span>%</p>
    </div>
    <div class="bg-yellow-50 rounded-xl p-6 text-center shadow hover:shadow-md transition">
      <h3 class="text-gray-600 font-medium mb-2">강수확률</h3>
      <p class="text-3xl font-bold text-yellow-500"><span id="rainChance">-</span>%</p>
    </div>
    <div class="bg-indigo-50 rounded-xl p-6 text-center shadow hover:shadow-md transition">
      <h3 class="text-gray-600 font-medium mb-2">날씨</h3>
      <p class="text-xl font-semibold text-indigo-600"><span id="skyCondition">-</span></p>
    </div>
    <div class="bg-gray-100 rounded-xl p-6 text-center shadow hover:shadow-md transition">
      <h3 class="text-gray-600 font-medium mb-2">강수형태</h3>
      <p class="text-xl font-semibold text-gray-700"><span id="precipitationType">-</span></p>
    </div>
  </div>

</section>

<!-- 🌍 스크립트 -->
<script>
document.addEventListener("DOMContentLoaded", () => {
  const contextPath = '<%= request.getContextPath() %>';

  if (!navigator.geolocation) {
    alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
    return;
  }

  navigator.geolocation.getCurrentPosition(
    (pos) => {
      const lat = pos.coords.latitude.toFixed(6);
      const lon = pos.coords.longitude.toFixed(6);
      console.log("📍 현재 위도:", lat, "현재 경도:", lon);

      const geoUrl =
        "https://nominatim.openstreetmap.org/reverse?lat=" +
        lat +
        "&lon=" +
        lon +
        "&format=json&accept-language=ko";
      const apiUrl = contextPath + "/api/main?lat=" + lat + "&lon=" + lon;

      console.log("🗺️ 주소 요청:", geoUrl);
      console.log("🌐 날씨 요청:", apiUrl);

      fetch(geoUrl)
        .then((res) => res.json())
        .then((data) => {
          const addr = data.address || {};
          const city =
            addr.city ||
            addr.state ||
            addr.region ||
            addr.province ||
            "";
          const district =
            addr.county ||
            addr.district ||
            addr.borough ||
            addr.suburb ||
            "";
          const shortAddress = [city, district].filter(Boolean).join(" ");
          document.getElementById("location").textContent =
            shortAddress || "주소 정보를 불러올 수 없습니다.";
          console.log("📍 표시 주소:", shortAddress);
        })
        .catch((err) => {
          console.error("❌ 위치 변환 오류:", err);
          document.getElementById("location").textContent =
            "주소 정보를 불러올 수 없습니다.";
        });

      fetch(apiUrl)
        .then((res) => {
          if (!res.ok) throw new Error("서버 오류: " + res.status);
          return res.json();
        })
        .then((data) => {
          console.log("✅ 날씨 응답:", data);
          document.getElementById("temp").textContent = data.temp ?? "-";
          document.getElementById("humidity").textContent = data.humidity ?? "-";
          document.getElementById("rainChance").textContent = data.rainChance ?? "-";
          document.getElementById("skyCondition").textContent = data.skyCondition ?? "-";
          document.getElementById("precipitationType").textContent = data.precipitationType ?? "-";

          // 🌤️ 3일 예보 렌더링
          const forecastContainer = document.getElementById("forecastContainer");
          forecastContainer.innerHTML = "";
          if (Array.isArray(data.forecastList)) {
            data.forecastList.forEach((f) => {
              const date = f.date ? f.date.substring(5).replace("-", "/") : "-";
              // ✅ 속성명 대소문자 불일치 대응 + 소수점 제거
              const min = (f.minTemp ?? f.mintemp ?? "-");
              const max = (f.maxTemp ?? f.maxtemp ?? "-");

              const card = document.createElement("div");
              card.className =
                "flex-shrink-0 bg-white border rounded-xl shadow p-6 text-center w-40 hover:shadow-md transition";
              card.innerHTML = `
                <h3 class="text-gray-500 mb-2 font-medium">${date}</h3>
                <div class="text-7xl mb-3">${empty f.skyEmoji ? "☁️" : f.skyEmoji}</div>
                <p class="text-lg text-gray-600 font-medium">${min}℃ ~ ${max}℃</p>
              `;
              forecastContainer.appendChild(card);
            });
          }
        })
        .catch((err) => {
          console.error("🚨 날씨 API 오류:", err);
        });
    },
    (err) => {
      console.error("❌ Geolocation 오류:", err);
      alert("위치 정보를 불러올 수 없습니다. 브라우저 권한을 허용하세요.");
    }
  );
});
</script>

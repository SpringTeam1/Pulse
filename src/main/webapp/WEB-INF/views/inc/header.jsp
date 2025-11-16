<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 애니메이션 css -->
<style>
    .slide-fade-out {
        opacity: 0;
        transform: translateY(-10px);
        transition: opacity 0.4s ease, transform 0.4s ease;
    }

    .slide-fade-in {
        opacity: 1;
        transform: translateY(0);
        transition: opacity 0.4s ease, transform 0.4s ease;
    }
</style>

<header class="bg-white shadow-md sticky top-0 z-50">
    <div class="w-full px-4 lg:px-6">
        <div class="flex justify-between items-center h-16">

            <!-- 🔥 로고 -->
            <div class="flex items-center space-x-6">
                <a href="${pageContext.request.contextPath}/index.do"
                   class="text-2xl font-bold text-brand hover:text-brand-dark transition">
                    Pulse🏃‍♂️‍
                </a>

                <!-- 🔥 네비게이션 (로고 오른쪽 붙임) -->
                <nav class="hidden md:flex space-x-6">

                    <!-- 📁 게시판 드롭다운 해당하는 도메인입력 -->
                    <div class="relative group">
                        <button class="text-gray-700 hover:text-brand font-medium">게시판</button>
                        <div class="absolute hidden group-hover:block bg-white border rounded-lg shadow-lg w-44 top-full left-0">
                            <a href="${pageContext.request.contextPath}/boardnotice/list.do"
                               class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">공지게시판</a>
                            <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">건의게시판</a>
                            <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-brand/10 hover:text-brand">코스게시판</a>
                        </div>
                    </div>

                    <!-- ✅ 커스텀 카테고리 추가 가이드
                          - 아래 예시처럼 새로운 대메뉴 추가 가능
                          - 링크는 Tiles 정의된 jsp로 연결 가능 (예: /crew.do → crew.jsp)
                        -->
                    <!--
                    <div class="relative group">
                      <button class="text-gray-700 hover:text-brand font-medium">Services</button>
                      <div class="absolute hidden group-hover:block bg-white border rounded-lg shadow-lg mt-2 w-44">
                        <a href="#" class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">Consulting</a>
                        <a href="#" class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">Development</a>
                      </div>
                    </div>
                    -->

                    <!-- 크루 해당하는 도메인입력 -->
                    <a href="${pageContext.request.contextPath}/crewmain.do"
                       class="text-gray-700 hover:text-brand font-medium">크루</a>

                    <!-- 코스 해당하는 도메인입력 -->
                    <a href="${pageContext.request.contextPath}/course/list.do"
                       class="text-gray-700 hover:text-brand font-medium">코스</a>

                    <!-- 🌡️ 자동 회전 날씨 정보 -->
                    <div id="weatherBox" class="text-gray-700 font-medium transition-opacity duration-500">
                        <span id="weatherLabel">날씨 불러오는중</span>
                        <span id="weatherValue" class="text-blue-500 font-semibold">..</span>
                    </div>

                </nav>
            </div>

            <!-- 🔥 로그인 네비게이션 영역 -->
            <nav class="hidden md:flex items-center space-x-5">

                <%
                    String nickname = (String) session.getAttribute("nickname");
                    boolean isLogin = nickname != null;
                %>

                <!-- 현재 로그인 상태 -->
                <span class="font-semibold text-brand-dark bg-white bg-brand/10 px-3 py-1 rounded-full">
		    현재 로그인 상태: <%= isLogin ? nickname : "게스트" %>
		  </span>

                <% if (isLogin) { %>

                <!-- 마이페이지 해당하는 도메인입력 -->
                <a href="${pageContext.request.contextPath}/mypage.do"
                   class="w-24 text-center px-3 py-1 rounded-full text-brand bg-white border border-brand hover:bg-brand/10 transition shadow-sm">
                    마이페이지
                </a>

                <!-- 로그아웃 해당하는 도메인입력 -->
                <a href="${pageContext.request.contextPath}/logout.do"
                   class="w-24 text-center px-3 py-1 rounded-full text-brand bg-white border border-brand hover:bg-brand/10 transition shadow-sm">
                    로그아웃
                </a>

                <% } else { %>

                <!-- 로그인 해당하는 도메인입력 -->
                <a href="${pageContext.request.contextPath}/login.do"
                   class="w-24 text-center px-3 py-1 rounded-full text-brand bg-white border border-brand hover:bg-brand/10 transition shadow-sm">
                    로그인
                </a>

                <!-- 회원가입 해당하는 도메인입력 -->
                <a href="${pageContext.request.contextPath}/register.do"
                   class="w-24 text-center px-3 py-1 rounded-full text-brand bg-white border border-brand hover:bg-brand/10 transition shadow-sm">
                    회원가입
                </a>

                <% } %>

            </nav>

            <!-- 🔥 모바일 햄버거 -->
            <div class="md:hidden">
                <button id="menuBtn" class="text-gray-600 hover:text-brand focus:outline-none">
                    <svg class="w-7 h-7" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 12h16M4 18h16" />
                    </svg>
                </button>
            </div>
        </div>
    </div>

    <!-- 🔥 모바일 메뉴 -->
    <div id="mobileMenu" class="hidden md:hidden bg-white border-t">

        <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">게시판</a>
        <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">크루</a>
        <a href="#" class="block px-4 py-3 text-gray-700 hover:bg-brand/10 hover:text-brand">코스</a>

        <!-- 모바일 로그인 영역 -->
        <div class="border-t mt-2">

            <div class="px-4 py-3 text-gray-800 font-medium">
                현재 로그인 상태: <%= isLogin ? nickname : "게스트" %>
            </div>

            <% if (isLogin) { %>
            <a href="${pageContext.request.contextPath}/mypage.do"
               class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">마이페이지</a>
            <a href="${pageContext.request.contextPath}/logout.do"
               class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">로그아웃</a>
            <% } else { %>
            <a href="${pageContext.request.contextPath}/login.do"
               class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">로그인</a>
            <a href="${pageContext.request.contextPath}/register.do"
               class="block px-4 py-2 hover:bg-brand/10 hover:text-brand">회원가입</a>
            <% } %>

        </div>
    </div>
</header>

<!-- 🔥 모바일 메뉴 토글 -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const btn = document.getElementById("menuBtn");
        const menu = document.getElementById("mobileMenu");

        btn?.addEventListener("click", () => {
            menu.classList.toggle("hidden");
        });
    });
</script>

<!-- 위치에 따른 날씨API -->
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

                        // 🔥 여기서 회전 애니메이션 실행!
                        startWeatherRotation(data);

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

<!-- 날씨 애니메이션 -->
<script>
    function startWeatherRotation(data) {
        const box = document.getElementById("weatherBox");
        const label = document.getElementById("weatherLabel");
        const value = document.getElementById("weatherValue");

        const items = [
            { label: "현재 기온🌡️:", value: data.temp + "℃" },
            { label: "습도💧:", value: data.humidity + "%" },
            { label: "강수확률☔:", value: data.rainChance + "%" },
            { label: "날씨🌤️:", value: data.skyCondition },
            { label: "강수형태🌧️:", value: data.precipitationType }
        ];

        let index = 0;

        function updateWeather() {

            // 🔥 슬라이드 + 페이드 아웃
            box.classList.remove("slide-fade-in");
            box.classList.add("slide-fade-out");

            setTimeout(() => {
                // 내용 변경
                label.textContent = items[index].label;
                value.textContent = items[index].value;

                // 🔥 슬라이드 + 페이드 인
                box.classList.remove("slide-fade-out");
                box.classList.add("slide-fade-in");

                index = (index + 1) % items.length;
            }, 400);
        }

        //최초 실행
        box.classList.add("slide-fade-in");
        updateWeather();

        //4초마다 교체
        setInterval(updateWeather, 4000);
    }
</script>
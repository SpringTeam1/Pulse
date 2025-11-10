<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ==========================================================
✅ crewregister.jsp (Tiles content only)
- HTML, HEAD, BODY는 layout.jsp에서 자동 처리
- TailwindCSS는 이미 로드되어 있음
- Kakao 지도 모바일 터치 대응 및 폼 검증 포함
========================================================== -->

<section class="max-w-3xl mx-auto mt-10 bg-white rounded-xl shadow p-6 space-y-6">

    <header>
        <h1 class="text-3xl font-bold text-brand mb-2">크루 생성하기</h1>
        <p class="text-gray-600">우리 크루의 이름, 소개, 활동 지역을 입력해주세요.</p>
    </header>

    <form method="POST" action="/pulse/crewregisterok"
          enctype="multipart/form-data"
          onsubmit="return validateForm()"
          class="space-y-6">

        <!-- 이름 -->
        <div>
            <label for="crewName" class="block text-lg font-semibold text-gray-800 mb-1">크루 이름</label>
            <input type="text" id="crewName" name="crewName" required
                   class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-brand-dark outline-none"
                   placeholder="">
        </div>

        <!-- 대표 이미지 -->
        <div>
            <label for="crewAttach" class="block text-lg font-semibold text-gray-800 mb-1">대표 사진</label>
            <input type="file" id="crewAttach" name="crewAttach" accept="image/*"
                   class="w-full border border-gray-300 rounded-lg p-3 cursor-pointer focus:ring-2 focus:ring-brand-dark outline-none">
        </div>

        <!-- 소개 -->
        <div>
            <label for="description" class="block text-lg font-semibold text-gray-800 mb-1">크루 소개</label>
            <textarea id="description" name="description" required
                      class="w-full border border-gray-300 rounded-lg p-3 resize-y min-h-[300px] focus:ring-2 focus:ring-brand-dark outline-none"
                      placeholder="크루 소개글 작성"></textarea>
        </div>

        <!-- 지도 -->
        <div>
            <label class="block text-lg font-semibold text-gray-800 mb-1">주요 활동 지역</label>
            <p class="text-sm text-gray-600 mb-2">지도에서 위치를 클릭해 활동 지역을 설정해주세요.</p>
            <p id="selectedAddress" class="text-sm text-brand-dark font-semibold mb-2"></p>
            <div id="map" class="w-full h-[400px] rounded-lg border border-gray-300"></div>
        </div>

        <!-- hidden inputs -->
        <input type="hidden" id="latitude" name="latitude">
        <input type="hidden" id="longitude" name="longitude">
        <input type="hidden" id="regionCity" name="regionCity">
        <input type="hidden" id="regionCounty" name="regionCounty">
        <input type="hidden" id="regionDistrict" name="regionDistrict">

        <!-- 제출 버튼 -->
        <div class="text-right">
            <button type="submit"
                    class="bg-brand text-white px-6 py-3 rounded-lg font-semibold hover:bg-brand-dark transition focus:outline-none">
                크루 생성하기
            </button>
        </div>
    </form>
</section>

<!-- ✅ Kakao Maps SDK (정상 동기 로드) -->
<script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=3b3e5434f5f49208ccb28bf6da30b8ec&libraries=services"></script>

<!-- ✅ 지도 및 폼 JS -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const mapContainer = document.getElementById('map');
        const latInput = document.getElementById('latitude');
        const lonInput = document.getElementById('longitude');
        const selectedAddress = document.getElementById('selectedAddress');
        const geocoder = new kakao.maps.services.Geocoder();

        // 지도 생성
        const map = new kakao.maps.Map(mapContainer, {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 기본 서울시청
            level: 5,
            draggable: true,               // ✅ 드래그 허용 (모바일 포함)
            scrollwheel: true,             // ✅ 확대/축소 허용
            disableDoubleClickZoom: false, // ✅ 더블탭 확대 허용
            keyboardShortcuts: false       // (선택) 키보드 단축키 비활성
        });

        // 기본 마커 설정
        const marker = new kakao.maps.Marker({
            position: map.getCenter(),
            image: new kakao.maps.MarkerImage(
                'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png',
                new kakao.maps.Size(24, 35)
            )
        });
        marker.setMap(map);

        // 지도 클릭 이벤트
        kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
            const latlng = mouseEvent.latLng;
            marker.setPosition(latlng);

            latInput.value = latlng.getLat().toFixed(6);
            lonInput.value = latlng.getLng().toFixed(6);

            // 역지오코딩: 좌표 -> 주소
            geocoder.coord2Address(latlng.getLng(), latlng.getLat(), function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    const addr = result[0].road_address
                        ? result[0].road_address.address_name
                        : result[0].address.address_name;

                    selectedAddress.textContent = "선택된 활동 지역: " + addr;

                    document.getElementById('regionCity').value = result[0].address.region_1depth_name;
                    document.getElementById('regionCounty').value = result[0].address.region_2depth_name;
                    document.getElementById('regionDistrict').value = result[0].address.region_3depth_name;
                }
            });
        });

        // ✅ 모바일 터치 제약 해제 (CSS 보조)
        mapContainer.style.touchAction = "auto";
        mapContainer.style.pointerEvents = "auto";
    });

    // 폼 검증
    function validateForm() {
        const lat = document.getElementById('latitude').value;
        if (!lat) {
            alert('지도에서 활동 지역을 선택해주세요.');
            return false;
        }
        return true;
    }
</script>

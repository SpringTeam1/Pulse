<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    document.querySelector("form").addEventListener("submit", async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        const formData = new FormData(e.target);
        const res = await fetch("/pulse/api/v1/crew", { method: "POST", body: formData });
        const data = await res.json();

        if (data.success) {
            alert(data.message);
            location.href = "/pulse/crewmain";
        } else {
            alert("❌ " + data.message);
        }
    });


</script>

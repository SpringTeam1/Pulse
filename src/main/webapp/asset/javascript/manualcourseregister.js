//manualcourseregister.js
kakao.maps.load(function() {
    // ===================================
    // 1. 지도 생성 및 전역 변수 초기화
    // ===================================
    const mapContainer = document.getElementById('map');
    const manualForm = $('#manualForm');
    const manualSubmitBtn = $('#manualSubmitBtn');

    const map = new kakao.maps.Map(mapContainer, {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 서울 시청
        level: 7
    });

    let manualCoords = []; // 서버로 보낼 좌표 배열 {lat, lon}
    let markers = [];      // 지도에 표시할 마커 배열
    let polyline = null;   // 지도에 그릴 선

    const MAX_POINTS = parseInt(manualForm.data('maxPoints')) || 7; // 폼에서 최대값 읽기

    // ===================================
    // 2. 이벤트 핸들러
    // ===================================

    // [1] 지도 클릭 이벤트 (단순화됨)
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
        
        if (markers.length >= MAX_POINTS) {
            alert(`지점은 최대 ${MAX_POINTS}개까지만 등록할 수 있습니다.`);
            return;
        }

        const latlng = mouseEvent.latLng;
        
        // 1. 좌표 배열에 추가 (서버 DTO와 일치하는 형태)
        manualCoords.push({
            lat: latlng.getLat(),
            lon: latlng.getLng() // (lon으로 저장)
        });

        // 2. 마커 생성 및 추가
        const marker = new kakao.maps.Marker({ position: latlng });
        marker.setMap(map);
        markers.push(marker);

        // 3. 경로 선 그리기
        drawPolyline();
    });

    // [2] 수동 등록 AJAX 전송
    manualSubmitBtn.on('click', function() {
        if (manualCoords.length < 2) {
            alert('경로 등록을 위해 최소 두 지점 이상을 클릭해주세요.');
            return;
        }
        
        // 1. 서버로 보낼 JSON 객체 생성 (ManualCourseRequest DTO 형태)
        const requestData = {
            coords: manualCoords, 
            courseName: $('#manualCourseName').val(),
            description: $('#manualDescription').val(),
            accountId: $('#manualAccountId').val()
        };

        // 2. AJAX 호출 (JSON 본문 전송)
        $.ajax({
            url: manualForm.data('manualApiUrl'), // /api/course/manual
            type: 'POST',
            data: JSON.stringify(requestData),
            contentType: 'application/json', // 👈 (중요) JSON 전송
            dataType: 'json', 

            success: function(savedCourse) {
                alert('수동 코스 등록 요청이 완료되었습니다. 관리자 승인 대기 중.');
                location.href = manualForm.data('redirectUrl'); // 메인으로 이동
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('수동 코스 등록에 실패했습니다.');
            }
        });
    });

    // =======================
    // 3. 보조(Helper) 함수
    // =======================
    function drawPolyline() {
        if (polyline) {
            polyline.setMap(null); // 기존 선 제거
        }
        
        if (manualCoords.length >= 2) {
            const path = manualCoords.map(coord => new kakao.maps.LatLng(coord.lat, coord.lon));
            
            polyline = new kakao.maps.Polyline({
                path: path,
                strokeWeight: 5,
                strokeColor: '#FF0000',
                map: map
            });
        }
    }
});
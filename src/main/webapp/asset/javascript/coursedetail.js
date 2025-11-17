// 'kakao.maps.load'로 감싸서 SDK 로드 경합 문제를 방지합니다.
kakao.maps.load(function() {

    // 1. JSP가 HTML에 숨겨둔 데이터 컨테이너를 찾습니다.
    const $dataContainer = $('#track-data-container');

    // 2. data-track-json 속성에서 'JSON 문자열'을 읽어옵니다.
    const spotList = $dataContainer.data('trackJson');

    if (spotList && spotList.length > 0) {
            drawCourseMap(spotList);
    } else {
        console.error("지도에 표시할 트랙 데이터(trackData)가 없습니다.");
    }
});

/**
 * 전달받은 좌표 목록(spotList)을 사용해 지도에 마커와 경로를 표시하는 함수
 * (이전과 달리, {lat, lon} 객체를 받음)
 */
function drawCourseMap(spotList) {
    const mapContainer = document.getElementById('map-container'); 

    const mapOption = {
        center: new kakao.maps.LatLng(spotList[0].lat, spotList[0].lon), 
        level: 8,
        draggable: true, // 사용자가 조작할 수 있도록 true로 변경 (선택)
        scrollwheel: true
    };

    const map = new kakao.maps.Map(mapContainer, mapOption);

    const path = []; 
    const bounds = new kakao.maps.LatLngBounds(); 

    spotList.forEach((spot, index) => {
        const position = new kakao.maps.LatLng(spot.lat, spot.lon);

        // (수정) 시작점과 끝점에만 마커를 표시
        if (index === 0 || index === spotList.length - 1) {
            const marker = new kakao.maps.Marker({
                position: position
            });
            marker.setMap(map);
        }

        path.push(position);
        bounds.extend(position);
    });

    // 경로(Polyline) 그리기
    if (path.length > 1) {
        const polyline = new kakao.maps.Polyline({
            path: path,
            strokeWeight: 5,
            strokeColor: '#FF0000',
            strokeOpacity: 0.7,
            map: map
        });
    }

    // 모든 경로가 보이도록 지도 범위 재설정
    map.setBounds(bounds);
}
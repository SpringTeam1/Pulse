<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="max-w-4xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-6">

    <div class="bg-white p-6 shadow-lg rounded-lg">
        <h2 class="text-2xl font-semibold text-gray-900 mb-4">🗺️ 운동 경로</h2>
        <div id="map-container" class="w-full h-96 ..."></div>
    </div>

    <div class="bg-white p-6 shadow-lg rounded-lg space-y-6">
        <h1 class="text-2xl font-bold ...">${workout.workoutDate} 운동 기록</h1>
        
        <%-- (수정) 부모 div의 space-y-4를 간격의 기준으로 삼습니다. --%>
        <div class="space-y-4 text-sm text-gray-700 flex flex-col">
            
            <div>
                <%-- (수정) 라벨에 mb-1을 주어 값과 구분 --%>
                <div class="font-semibold mb-1">총 거리</div>
                <%-- (수정) py-2 제거, ml-2로 들여쓰기 --%>
                <div class="ml-2"><fmt:formatNumber value="${workout.totalDistance}" pattern="#.##" /> km</div>
            </div>
            
            <div>
                <div class="font-semibold mb-1">운동 시간</div>
                <div class="ml-2"><fmt:formatNumber value="${workout.totalTime / 60}" pattern="0" /> 분</div>
            </div>
            
            <div>
                <div class="font-semibold mb-1">평균 페이스</div>
                <div class="ml-2"><fmt:formatNumber value="${workout.avgPace}" pattern="0.00" /> 분/km</div>
            </div>
            
            <div>
                <div class="font-semibold mb-1">소모 칼로리</div>
                <div class="ml-2">${workout.totalCalories} kcal</div>
            </div>
        </div>

        <div class="pt-4 border-t border-gray-200 mt-4 text-gray-700">
            <div class="font-semibold mb-1">코멘트</div>
            <p class="text-gray-600">${workout.exerciseComment}</p>
        </div>
    </div>

    <div id="track-data-container" 
         data-track-json='<c:out value="${workout.trackData}" escapeXml="false" />'
         class="hidden">
    </div>

</div>
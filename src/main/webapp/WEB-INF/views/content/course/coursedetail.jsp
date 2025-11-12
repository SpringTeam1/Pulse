<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="max-w-4xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-6">

    <div class="bg-white p-6 shadow-lg rounded-lg">
        <h2 class="text-2xl font-semibold text-gray-900 mb-4">🗺️ 코스 정보</h2>
        <div id="map-container" 
             class="w-full h-96 rounded-md border border-gray-300 shadow-sm">
            </div>
    </div>
    
    <div class="bg-white p-6 shadow-lg rounded-lg space-y-4">
        <h1 class="text-2xl font-bold text-gray-900 pb-4">${course.courseName}</h1>
        
        <div class="space-y-4 text-sm text-gray-700 flex flex-col">
            
            <div>
                <div class="font-semibold">총 거리</div>
                <div class="py-2"><fmt:formatNumber value="${course.courseLength}" pattern="#.##" /> km</div>
            </div>
            
            <div>
                <div class="font-semibold">등록자</div>
                <div class="py-2">${course.accountId}</div>
            </div>
            
            <div>
                <div class="font-semibold">출발지</div>
                <div class="py-2">${course.startAddress}</div>
            </div>
            
            <div>
                <div class="font-semibold">도착지</div>
                <div class="py-2">${course.endAddress}</div>
            </div>
            <div>
                <div class="font-semibold">설명</div>
                <p class="py-2">${course.description}</p>
            </div>
        </div>
        
        
    </div>

	
    <div id="track-data-container" 
         data-track-json='<c:out value="${course.trackData}" escapeXml="false" />'
         class="hidden">
    </div>
    
</div>
<div class="max-w-4xl mx-auto mt-8 flex justify-end">
    
    <a href="<c:url value='/course/list' />"
       class="inline-flex items-center justify-center py-2 px-4 
              border border-transparent shadow-sm 
              text-sm font-medium rounded-md text-white 
              bg-indigo-600 hover:bg-indigo-700">
        목록으로
    </a>
    
</div>

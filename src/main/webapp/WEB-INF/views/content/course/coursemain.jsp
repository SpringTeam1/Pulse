<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="max-w-4xl mx-auto mt-10 text-center space-y-6">
    <div class="text-center space-y-6 py-10 bg-indigo-50 rounded-2xl">
        <h1 class="text-4xl font-bold text-gray-900">어디로 달리고 싶으신가요?</h1>
        <p class="text-gray-600 text-lg">지역명, 코스 이름으로 러닝 코스를 찾아보세요.</p>

        <form action="<c:url value='/course/list' />" method="GET" class="max-w-2xl mx-auto flex space-x-2">
            <div class="relative flex items-center w-full">
                <input type="text" name="keyword" placeholder="코스명, 출발·도착지로 검색하세요." 
                    class="flex-grow w-full rounded-md border-gray-300 shadow-sm px-4 py-3 text-base 
                            focus:ring-2 focus:ring-blue-500 focus:border-blue-500 focus:outline-none">
                
                <button type="submit" 
                        class="inline-flex items-center justify-center px-6 py-3 border border-transparent 
                            text-base font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 
                            shadow-md transition duration-150 ease-in-out
                            flex-shrink-0 whitespace-nowrap">
                    검색
                </button>
            </div>
        </form>
    </div>
    
    <section>
        <div class="flex items-center mb-6">
            <span class="text-2xl mr-2">🔥</span>
            <h2 class="text-2xl font-bold text-gray-900">지금 가장 인기있는 코스</h2>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <c:forEach var="course" items="${popularList}">
                <a href="<c:url value='/course/detail/${course.courseSeq}' />" 
                   class="block bg-white rounded-xl shadow-lg hover:shadow-2xl transition duration-500 transform hover:scale-[1.02]">
                   
                    <div class="p-5 space-y-4">
                        <h3 class="text-xl font-bold text-gray-900 hover:text-indigo-600 truncate pb-2 border-b border-gray-200">
                            ${course.courseName}
                        </h3>
                        <div class="space-y-2 text-sm text-gray-700">
                            <div>
                                <div class="font-medium">총 거리:
                                <span class="text-gray-900">
                                    <fmt:formatNumber value="${course.courseLength}" pattern="#.##" /> km
                                </span>
                                </div>
                            </div>
                            <div>
                                <div class="font-medium">출발지:
                                <span class="text-gray-900 truncate">${course.startAddress}</span>
                                </div>
                            </div>
                            <div>
                                <div class="font-medium">도착지:
                                <span class="text-gray-900 truncate">${course.endAddress}</span>
                                </div>
                            </div>
                        </div>
                        <div class="flex items-center text-sm pt-3 border-t border-gray-200 mt-3">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" fill="#3B82F6" viewBox="0 0 20 20">
                                <path d="M5 4a2 2 0 012-2h6a2 2 0 012 2v14l-5-2.5L5 18V4z" />
                            </svg>
                            <span class="font-semibold text-gray-900">${course.favoriteCount}</span>
                            <span class="ml-1 text-gray-700">스크랩</span>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </section>

    <c:if test="${not empty recommendedList}">
        <section>
            <div class="flex items-center mb-6">
                <span class="text-2xl mr-2">📍</span>
                <h2 class="text-2xl font-bold text-gray-900">회원님을 위한 지역 기반 추천 코스</h2>
            </div>
            
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <c:forEach var="course" items="${recommendedList}">
                    <a href="<c:url value='/course/detail/${course.courseSeq}' />" 
                       class="block bg-white rounded-xl shadow-lg hover:shadow-2xl transition duration-500 transform hover:scale-[1.02]">
                       
                        <div class="p-5 space-y-4">
                            <h3 class="text-xl font-bold text-gray-900 hover:text-indigo-600 truncate pb-2 border-b border-gray-200">
                                ${course.courseName}
                            </h3>
                            <div class="space-y-2 text-sm text-gray-700">
                                <div>
                                    <div class="font-medium">총 거리:&nbsp;
	                                    <span class="text-gray-900 ml-2">
	                                        <fmt:formatNumber value="${course.courseLength}" pattern="#.##" /> km
	                                    </span>
                                    </div>
                                </div>
                                <div>
                                    <div class="font-medium">출발지:</div>
                                    <div class="text-gray-900 ml-2 truncate">${course.startAddress}</div>
                                </div>
                            </div>
                            <div class="flex items-center text-sm pt-3 border-t border-gray-200 mt-3">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" fill="#3B82F6" viewBox="0 0 20 20">
                                    <path d="M5 4a2 2 0 012-2h6a2 2 0 012 2v14l-5-2.5L5 18V4z" />
                                </svg>
                                <span class="font-semibold text-gray-900">${course.favoriteCount}</span>
                                <span class="ml-1 text-gray-700">스크랩</span>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </section>
    </c:if>
    
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="max-w-7xl mx-auto p-6 space-y-8">
    <h2 class="text-3xl font-bold text-gray-900 border-b pb-4">전체 코스 목록</h2>

    <form id="searchBox" action="<c:url value='/course/list' />" method="GET" class="flex space-x-2">
        <input type="text" id="searchKeyword" name="keyword" placeholder="검색어 입력" value="${keyword}"
               class="flex-grow rounded-md border-gray-300 shadow-sm px-3 py-2 sm:text-sm focus:ring-2 focus:ring-blue-500">
        <button type="submit" id="searchButton"
                class="inline-flex items-center justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700">
            검색
        </button>
    </form>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
    
    <c:choose>
        <c:when test="${not empty courseList}">
            <c:forEach var="course" items="${courseList}">
                <a href="<c:url value='/course/detail/${course.courseSeq}' />" 
                   class="block bg-white rounded-xl shadow-lg hover:shadow-2xl transition duration-500 transform hover:scale-[1.02]">
                   
                    <div class="p-5 space-y-3">
                        
                        <h3 class="text-xl font-bold text-gray-900 hover:text-indigo-600 truncate pb-2 border-b">
                            ${course.courseName}
                        </h3>

                        <div class="grid grid-cols-2 gap-x-4 gap-y-1 text-sm text-gray-700">
                            
                            <span class="font-medium">총 거리:</span>
                            <span class="text-right text-gray-900">
                                ${course.courseLength} km
                            </span>
                            
                            <span class="font-medium">등록자:</span>
                            <span class="text-right">${course.accountId}</span>
                            
                            <span class="font-medium">출발지:</span>
                            <span class="text-right truncate">${course.startAddress}</span>

                            <span class="font-medium">도착지:</span>
                            <span class="text-right truncate">${course.endAddress}</span>
                        </div>

                        <div class="flex items-center text-sm text-gray-500 pt-3 border-t mt-3">
                            <svg xmlns="http://www.w3.org/2000/svg" 
                                 class="h-5 w-5 mr-1 fill-[#1da1f2]" fill="#1da1f2" viewBox="0 0 20 20">
                                <path d="M5 4a2 2 0 012-2h6a2 2 0 012 2v14l-5-2.5L5 18V4z" />
                            </svg>
                            <span class="ml-1 text-gray-700">스크랩</span>
                            <span class="font-semibold text-gray-900">&nbsp;${course.favoriteCount}</span>
                        </div>
                    </div>
                </a>
                </c:forEach>
        </c:when>
        <c:otherwise>
            <p class="text-gray-600">현재 등록된 코스가 없습니다.</p>
        </c:otherwise>
    </c:choose>

    </div>
    
    <div class="pagination flex justify-center space-x-2 mt-8 text-sm">
        <c:if test="${pageDTO.startPage > 1}">
            <a href="<c:url value='/course/list?page=${pageDTO.startPage - 1}' />" class="py-2 px-3 border rounded-md hover:bg-gray-100">&laquo;</a>
        </c:if>

        <c:forEach var="p" begin="${pageDTO.startPage}" end="${pageDTO.endPage}">
            <a href="<c:url value='/course/list?page=${p}' />" 
               class="py-2 px-3 border rounded-md hover:bg-gray-100 ${pageDTO.currentPage == p ? 'bg-indigo-600 text-white' : ''}">
                ${p}
            </a>
        </c:forEach>

        <c:if test="${pageDTO.endPage < pageDTO.totalPage}">
            <a href="<c:url value='/course/list?page=${pageDTO.endPage + 1}' />" class="py-2 px-3 border rounded-md hover:bg-gray-100">&raquo;</a>
        </c:if>
    </div>
</div>
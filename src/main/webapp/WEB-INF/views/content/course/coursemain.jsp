<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="max-w-4xl mx-auto mt-10 text-center space-y-6">
    
    <h1 class="text-4xl font-bold text-gray-900">
        어디로 달리고 싶으신가요?
    </h1>
    <p class="text-gray-600 text-lg">지역명, 코스 이름으로 러닝 코스를 찾아보세요.</p>

    <form action="<c:url value='/course/list' />" method="GET" class="max-w-2xl mx-auto flex space-x-2">
        
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
    </form>

</div>
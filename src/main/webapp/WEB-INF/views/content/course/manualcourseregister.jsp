<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="max-w-4xl mx-auto bg-white p-8 shadow-lg rounded-lg"> 

    <div class="border-b border-gray-200 pb-4 mb-6">
        <h2 class="text-2xl font-semibold text-gray-900">🗺️ 직접 코스 등록하기</h2>
        <p class="mt-2 text-sm text-gray-600" id="guide-message">지도에서 원하는 위치를 클릭하여 경로(최대 7개)를 등록하세요.</p>
    </div>

    <form id="manualForm"
        data-manual-api-url="<c:url value='/api/course/manual' />" 
        data-redirect-url="<c:url value='/course/main' />"
        data-max-points="7"
        class="space-y-6">

        <div>
            <label class="block text-sm font-medium text-gray-700">코스 지도 (클릭하여 지점 추가)</label>
            <div id="map" class="mt-1 w-full h-96 rounded-md border border-gray-300"></div>

            <div id="map-error" class="hidden mt-1 text-sm text-red-500"></div>

            <div id="realtime-address-display" class="mt-2 p-3 bg-gray-50 border border-gray-100 rounded-md text-sm text-gray-700">
            	지도에서 위치를 클릭하세요.
            </div> 
        </div>

        <div>
            <label for="manualCourseName" class="block text-sm font-medium text-gray-700">코스 이름<span class="text-red-500"> *</span></label>
            <input type="text" id="manualCourseName" name="courseName"
                   maxlength="30" required placeholder="코스 이름을 입력해주세요"
                   class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-3 py-2 focus:outline-none focus:ring-2 focus:border-blue-500 focus:ring-blue-500 sm:text-sm">
            <p id="manualCourseName-error" class="hidden mt-1 text-sm text-red-500"></p>
        </div>  

        <div>
            <label for="manualDescription" class="block text-sm font-medium text-gray-700">코스 설명</label>
            <textarea id="manualDescription" name="description" rows="3" 
                      maxlength="666"
                      placeholder="코스 설명을 입력해주세요"
                      class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-3 py-2
                             focus:outline-none focus:ring-2 focus:border-blue-500 focus:ring-blue-500 sm:text-sm"></textarea>
        </div>
        
        <!-- 임시 하드코딩, 세션에서 받아오는 방식으로 수정 필요 -->
        <input type="hidden" id="manualAccountId" name="accountId" value="hong">
        
        <div class="text-right"> 
            <button type="button" id="manualSubmitBtn"
                    class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm 
                           text-sm font-medium rounded-md text-white 
                           bg-indigo-600 hover:bg-indigo-700 
                           focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                코스 등록 요청하기
            </button>
        </div>
    </form>
</div>
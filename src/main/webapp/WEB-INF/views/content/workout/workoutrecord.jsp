<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="max-w-2xl mx-auto bg-white p-8 shadow-lg rounded-lg">

    <div class="border-b border-gray-200 pb-4 mb-6">
        <h2 class="text-2xl font-semibold text-gray-900">🏃‍♂️ 운동 기록 첨부하기</h2>
        <p class="mt-2 text-sm text-gray-600">GPX 파일을 첨부하여 운동 기록을 저장합니다.</p>
    </div>

    <form id="gpxRecordForm" 
        enctype="multipart/form-data"
        data-api-url="<c:url value='/api/workout/record/gpx' />" 
        data-redirect-url="<c:url value='/workout/log' />" 
        class="space-y-6">

        <div>
            <label for="gpxFiles" class="block text-sm font-medium text-gray-700">
                GPX 파일<span class="text-red-500">&nbsp;*</span>
            </label>
            <input type="file" id="gpxFiles" name="gpxFiles" accept=".gpx" required multiple
                   class="mt-1 block w-full text-sm text-gray-500
                          file:mr-4 file:py-2 file:px-4
                          file:rounded-md file:border-0
                          file:bg-indigo-50 file:text-indigo-700
                          hover:file:bg-indigo-100">
            <p id="gpx-error" class="hidden mt-1 text-sm text-red-600"></p>
        </div>

        <div>
            <label for="userWeight" class="block text-sm font-medium text-gray-700">
                체중(kg)<span class="text-red-500">&nbsp;*</span>
            </label>
            <input type="number" id="userWeight" name="userWeight" required
                   placeholder="칼로리 계산에 필요합니다 (예: 70)"
                   class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-3 py-2
                          focus:outline-none focus:ring-2 focus:border-blue-500 focus:ring-blue-500 sm:text-sm">
            <p id="weight-error" class="hidden mt-1 text-sm text-red-500"></p>
        </div>

        <div>
            <label for="attachment" class="block text-sm font-medium text-gray-700">사진</label>
            <input type="file" id="attachment" name="attachment" accept="image/*"
                   class="mt-1 block w-full text-sm text-gray-500
                          file:mr-4 file:py-2 file:px-4
                          file:rounded-md file:border-0
                          file:bg-gray-50 file:text-gray-700
                          hover:file:bg-gray-100">
        </div>

        <div>
            <label for="exerciseComment" class="block text-sm font-medium text-gray-700">운동 기록 코멘트</label>
            <textarea id="exerciseComment" name="exerciseComment" rows="3"
                      placeholder="오늘의 운동에 대해 기록해보세요."
                      class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-3 py-2
                             focus:outline-none focus:ring-2 focus:border-blue-500 focus:ring-blue-500 sm:text-sm"></textarea>
        </div>

        <div class="text-right">
            <button type="button" id="gpxSubmitBtn"
                    class="inline-flex justify-center py-2 px-4 
                           border border-transparent shadow-sm 
                           text-sm font-medium rounded-md text-white 
                           bg-indigo-600 hover:bg-indigo-700 
                           focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                운동 기록 저장하기
            </button>
        </div>
        
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</div>
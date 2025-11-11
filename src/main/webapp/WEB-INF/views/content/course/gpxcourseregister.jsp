<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="max-w-2xl mx-auto bg-white p-8 shadow-lg rounded-lg">

	<div class="border-b border-gray-200 pb-4 mb-6">
		<h2 class="text-2xl font-semibold text-gray-900">🗺️ GPX 코스 등록하기</h2>
		<p class="mt-2 text-sm text-gray-600">GPX 파일을 첨부하고, 코스 정보를 입력해주세요.</p>
	</div>

	<form id="gpxRegisterForm" enctype="multipart/form-data"
		data-api-url="<c:url value='/api/course/gpx' />"
		data-redirect-url="<c:url value='/' />" class="space-y-6">

		<div>
		    <label for="courseName"
		        class="block text-sm font-medium text-gray-700">코스 이름</label> 
		    <input
		        type="text" id="courseName" name="courseName"
		        maxlength="30"
				placeholder="코스 이름을 입력해주세요"
		        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm 
		               sm:text-sm p-2
		               focus:border-gray-500
		               focus:ring-gray-500
		               focus:ring-1
		               focus:outline-none">
		</div>

		<div>
			<label for="description"
				class="block text-sm font-medium text-gray-700">코스 설명</label>
			<textarea id="description" name="description" rows="3"
				maxlength="650"
				placeholder="코스 설명을 입력해주세요"
				class="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2
				focus:border-gray-500 focus:ring-gray-500 focus:ring-1 focus:outline-none sm:text-sm"></textarea>
		</div>

		<div>
			<label for="gpxFile" class="block text-sm font-medium text-gray-700">GPX
				파일</label>
				<input type="file" id="gpxFile" name="gpxFile" accept=".gpx"
				required
				class="mt-1 block w-full text-sm text-gray-500
                          file:mr-4 file:py-2 file:px-4 
                          file:rounded-md file:border-0
                          file:bg-indigo-50 file:text-indigo-700
                          hover:file:bg-indigo-100">
		</div>

		<input type="hidden" id="accountId" name="accountId" value="hong">

		<div class="text-right">
			<button type="button" id="gpxSubmitBtn"
				class="inline-flex justify-center py-2 px-4 
                           border border-transparent shadow-sm 
                           text-sm font-medium rounded-md text-white 
                           bg-indigo-600 hover:bg-indigo-700 
                           focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
				코스 등록 요청하기</button>
		</div>
	</form>
</div>
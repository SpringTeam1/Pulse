<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-3xl mx-auto mt-10 bg-white rounded-xl shadow p-6 space-y-6">

    <header>
        <h1 class="text-3xl font-bold text-brand mb-2">크루 생성하기</h1>
        <p class="text-gray-600">우리 크루의 이름, 소개, 활동 지역을 입력해주세요.</p>
    </header>

    <form method="POST" action="/pulse/api/v1/crew"
          enctype="multipart/form-data"
          onsubmit="return validateForm()"
          class="space-y-6">

        <!-- 이름 -->
        <div>
            <label for="crewName" class="block text-lg font-semibold text-gray-800 mb-1">크루 이름</label>
            <input type="text" id="crewName" name="crewName" required
                   class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-brand-dark outline-none"
                   placeholder="">
        </div>

        <!-- 대표 이미지 -->
        <div>
            <label for="crewAttach" class="block text-lg font-semibold text-gray-800 mb-1">대표 사진</label>
            <input type="file" id="crewAttach" name="crewAttach" accept="image/*"
                   class="w-full border border-gray-300 rounded-lg p-3 cursor-pointer focus:ring-2 focus:ring-brand-dark outline-none">
        </div>

        <!-- 소개 -->
        <div>
            <label for="description" class="block text-lg font-semibold text-gray-800 mb-1">크루 소개</label>
            <textarea id="description" name="description" required
                      class="w-full border border-gray-300 rounded-lg p-3 resize-y min-h-[300px] focus:ring-2 focus:ring-brand-dark outline-none"
                      placeholder="크루 소개글 작성"></textarea>
        </div>

        <!-- 지도 -->
        <div>
            <label class="block text-lg font-semibold text-gray-800 mb-1">주요 활동 지역</label>
            <p class="text-sm text-gray-600 mb-2">지도에서 위치를 클릭해 활동 지역을 설정해주세요.</p>
            <p id="selectedAddress" class="text-sm text-brand-dark font-semibold mb-2"></p>
            <div id="map" class="w-full h-[400px] rounded-lg border border-gray-300"></div>
        </div>

        <!-- hidden inputs -->
        <input type="hidden" id="latitude" name="latitude">
        <input type="hidden" id="longitude" name="longitude">
        <input type="hidden" id="regionCity" name="regionCity">
        <input type="hidden" id="regionCounty" name="regionCounty">
        <input type="hidden" id="regionDistrict" name="regionDistrict">

        <!-- 제출 버튼 -->
        <div class="text-right">
            <button type="submit"
                    class="bg-brand text-white px-6 py-3 rounded-lg font-semibold hover:bg-brand-dark transition focus:outline-none">
                크루 생성하기
            </button>
        </div>
    </form>
</section>


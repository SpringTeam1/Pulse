<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section class="max-w-5xl mx-auto mt-10 bg-white rounded-2xl shadow-lg p-8 space-y-6">
    <h1 class="text-3xl font-bold text-gray-900 flex items-center gap-2">
        <svg xmlns="http://www.w3.org/2000/svg" class="w-7 h-7 text-blue-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 10h.01M12 10h.01M16 10h.01M9 16h6M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        크루 가입 요청 관리
    </h1>

    <!-- 요약 카드 -->
    <div id="summary" class="grid grid-cols-1 sm:grid-cols-3 gap-4">
        <div class="bg-blue-50 border border-blue-200 rounded-xl p-4 text-center">
            <h3 class="text-blue-600 font-semibold text-sm">총 요청 수</h3>
            <p id="totalCount" class="text-3xl font-bold text-gray-900 mt-1">0</p>
        </div>
        <div class="bg-yellow-50 border border-yellow-200 rounded-xl p-4 text-center">
            <h3 class="text-yellow-600 font-semibold text-sm">승인 대기</h3>
            <p id="pendingCount" class="text-3xl font-bold text-gray-900 mt-1">0</p>
        </div>
        <div class="bg-green-50 border border-green-200 rounded-xl p-4 text-center">
            <h3 class="text-green-600 font-semibold text-sm">처리 완료</h3>
            <p id="doneCount" class="text-3xl font-bold text-gray-900 mt-1">0</p>
        </div>
    </div>

    <!-- 테이블 -->
    <div class="overflow-x-auto rounded-xl border border-gray-200 shadow-sm mt-6">
        <table class="w-full text-sm text-center border-collapse">
            <thead class="bg-blue-500 text-white">
            <tr class="text-sm font-semibold">
                <!-- ID, 상태는 모바일에서 숨김 -->
                <th class="py-3 px-4 border-r border-blue-400 hidden sm:table-cell">ID</th>
                <th class="py-3 px-4 border-r border-blue-400">닉네임</th>
                <th class="py-3 px-4 border-r border-blue-400 hidden sm:table-cell">상태</th>
                <th class="py-3 px-4">관리</th>
            </tr>
            </thead>
            <tbody id="joinTable" class="text-gray-700 bg-white divide-y divide-gray-100">
            <tr><td colspan="4" class="py-6 text-gray-400">불러오는 중...</td></tr>
            </tbody>
        </table>
    </div>
</section>

<style>
    /* tbody 행 hover 시 강조 */
    tbody tr:hover {
        background-color: #f9fafb;
        transition: background-color 0.2s ease;
    }
</style>


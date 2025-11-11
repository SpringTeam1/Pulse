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

<script>
    const crewSeq = "${crewSeq}";
    const table = document.getElementById("joinTable");

    async function loadRequests() {
        try {
            const res = await fetch(`/pulse/api/v1/crewjoin/list/` + crewSeq);
            const list = await res.json();
            console.log("✅ API 응답:", list);

            table.innerHTML = "";
            if (!list.length) {
                table.innerHTML = `<tr><td colspan="4" class="py-6 text-gray-400">가입 요청이 없습니다.</td></tr>`;
                return;
            }

            let pending = 0, done = 0;

            list.forEach(dto => {
                if (dto.requestState.trim() === "대기") pending++;
                else done++;

                const stateBadge =
                    dto.requestState.trim() === "대기"
                        ? `<span class="px-3 py-1 bg-yellow-100 text-yellow-800 text-xs font-medium rounded-full">대기</span>`
                        : `<span class="px-3 py-1 bg-green-100 text-green-800 text-xs font-medium rounded-full">${dto.requestState}</span>`;

                const controls =
                    dto.requestState.trim() === "대기"
                        ? `<div class="flex gap-2 justify-center">
                <button class="px-4 py-1.5 bg-green-500 text-white rounded-lg shadow-sm hover:bg-green-600 active:scale-95 transition"
                        onclick="approveJoin('\${dto.crewJoinSeq}','\${crewSeq}')">승인</button>
                <button class="px-4 py-1.5 bg-red-500 text-white rounded-lg shadow-sm hover:bg-red-600 active:scale-95 transition"
                        onclick="rejectJoin('\${dto.crewJoinSeq}')">거절</button>
               </div>`
                        : `<span class="text-gray-400 italic">처리 완료</span>`;

                table.insertAdjacentHTML(
                    "beforeend",
                    `
          <tr class="hover:bg-gray-50 transition">
            <td class="py-3 px-4 hidden sm:table-cell">\${dto.accountId}</td>
            <td class="py-3 px-4">\${dto.nickname}</td>
            <td class="py-3 px-4 hidden sm:table-cell">\${stateBadge}</td>
            <td class="py-3 px-4">\${controls}</td>
          </tr>
          `
                );
            });

            document.getElementById("totalCount").textContent = list.length;
            document.getElementById("pendingCount").textContent = pending;
            document.getElementById("doneCount").textContent = done;

        } catch (err) {
            console.error("❌ 목록 로드 오류:", err);
            table.innerHTML = `<tr><td colspan="4" class="py-6 text-red-500">목록을 불러오지 못했습니다.</td></tr>`;
        }
    }

    async function approveJoin(seq, crewSeq) {
        if (!confirm("가입 요청을 승인하시겠습니까?")) return;
        await fetch(`/pulse/api/v1/crewjoin/approve/` + seq, { method: "POST" });
        await fetch(`/pulse/api/v1/crewjoin/approve/memberup/` + crewSeq, { method: "PATCH" });
        loadRequests();
    }

    async function rejectJoin(seq) {
        if (!confirm("가입 요청을 거절하시겠습니까?")) return;
        await fetch(`/pulse/api/v1/crewjoin/reject/` + seq, { method: "POST" });
        loadRequests();
    }

    loadRequests();
</script>

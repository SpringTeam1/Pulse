<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

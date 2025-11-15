<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

    document.addEventListener("DOMContentLoaded", initDashBoard)

    async function initDashBoard() {
        const crewSeq = "${crewSeq}";

        // 1) 총 게시글 수
        loadTotalPostCount(crewSeq);
        // 2) 이번주 게시글 수
        loadWeeklyTop2count(crewSeq);

        loadTopViewPost(crewSeq);

        loadLikeTop(crewSeq);

        loadMarathon();

        await loadRecentMessages();

        connectSSE();
    }

    async function loadTotalPostCount(crewSeq) {
        try {
            const rs = await fetch(`/pulse/api/v1/crew/board/boardcount/\${crewSeq}`);
            const data = await rs.json();

            document.getElementById("post-total").innerText = data;
        } catch (err) {
            console.error("❌ 총 게시글 수 가져오기 실패:", err);
            document.getElementById("post-total").innerText = "-";
        }

    }

    async function loadWeeklyTop2count(crewSeq) {
        try {
            const rs = await fetch(`/pulse/api/v1/crew/board/boardtop2count/\${crewSeq}`);
            const data = await rs.json();

            document.getElementById("post-weekly").innerText = data;

        } catch (err) {
            console.error("❌ TOP2 게시글 가져오기 실패:", err);
            document.getElementById("post-weekly").innerText = "-";
        }
    }


    async function loadTopViewPost(crewSeq) {
        try {
            const res = await fetch(`/pulse/api/v1/crew/board/boardtop2/\${crewSeq}`);
            const dto = await res.json();

            if (!dto) return;

            document.getElementById("post-topview-title").innerText = dto.title || "-";
            document.getElementById("post-topview-writer").innerText = dto.nickname || "익명";
            document.getElementById("post-topview-count").innerText = dto.readCount || 0;

        } catch (err) {
            console.error("❌ 조회수 Top 가져오기 실패:", err);
        }
    }

    async function loadLikeTop(crewSeq) {
        try {
            const res = await fetch(`/pulse/api/v1/crew/board/boardliketop/\${crewSeq}`)
            const data = await res.json();

            if (!data) return;
            document.getElementById("post-mostlike-title").innerText = data.title || "-";
            document.getElementById("post-mostlike-writer").innerText = data.content || "-"
            document.getElementById("post-mostlike-count").innerText = data.favoriteCount || 0;
        } catch (err) {
            console.error("좋아요 가져오기 실패", err)
        }
    }

    async function loadMarathon() {
        try {
            const res = await fetch(`/pulse/api/v1/crew/marathonapi`);
            const marathon = await res.json();


            if (!marathon) return;
            const event = marathon.data[0];
            console.log(event)

            document.getElementById("event-name").innerText = event["대회명"] || "-";
            document.getElementById("event-date").innerText = event["대회일시"] || "-";
            document.getElementById("event-location").innerText = event["대회장소"] || "-";

        } catch (err) {
            console.error("마라톤 데이터 불러오기 실패", err);
        }
    }

    const loginId = "${accountId}";
    const crewSeq = "${crewSeq}";

    async function loadRecentMessages() {
        try {
            const res = await fetch(`/pulse/api/v1/crew/chat/recent/\${crewSeq}`);
            const list = await res.json();

            if (!list) return;

            // 과거 메시지부터 순서대로 렌더링
            list.forEach(msg => renderMessage(msg));

        } catch (err) {
            console.error("❌ 최근 메시지 불러오기 실패:", err);
        }
    }

    function renderMessage(msg) {
        const box = document.getElementById("chat-box");
        const isMine = msg.senderId === loginId;
        const time = msg.timestamp?.substring(11, 16) || "";
        let html = "";

        if (isMine) {
            // 🔵 내 메시지 (오른쪽)
            html = `
            <div class="flex justify-end">
                <div class="max-w-[70%] text-right">
                    <p class="text-xs text-slate-400 mb-1">\${time}</p>
                    <div class="bg-blue-500 text-white px-4 py-2 rounded-xl inline-block">
                        \${msg.message}
                    </div>
                </div>
            </div>`;
        } else {
            // ⚪ 상대 메시지 (왼쪽)
            html = `
            <div class="flex items-start gap-2">
                <img src="\${msg.profileUrl}"
                     class="w-8 h-8 rounded-full" />
                <div>
                    <p class="text-xs font-semibold text-slate-600 mb-1">\${msg.nickname}</p>
                    <div class="bg-slate-100 text-slate-800 px-4 py-2 rounded-xl inline-block">
                        \${msg.message}
                    </div>
                    <p class="text-[10px] text-slate-400 mt-1">\${time}</p>
                </div>
            </div>`;
        }

        // DOM에 추가
        box.insertAdjacentHTML("beforeend", html);

        // 스크롤 바닥으로 유지
        box.scrollTop = box.scrollHeight;
    }

    document
        .getElementById("chat-input")
        .addEventListener("keypress", async (e) => {
            if (e.key === "Enter") {
                const text = e.target.value.trim();
                if (!text) return;

                const payload = {
                    senderId: loginId,
                    nickname: "${nickname}",
                    profileUrl: "${profileUrl}",
                    message: text,
                };

                await fetch(`/pulse/api/v1/crew/chat/send/\${crewSeq}`, {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(payload),
                });

                e.target.value = "";
            }
        });

    function connectSSE() {
        const eventSource = new EventSource(`/pulse/api/v1/crew/chat/stream/\${crewSeq}`);

        eventSource.addEventListener("chat", (e) => {
            renderMessage(JSON.parse(e.data));
        });

        eventSource.onerror = () => {
            console.warn("SSE 연결 끊김 - 3초 후 재연결 예정...");
            setTimeout(connectSSE, 3000);
        };
    }

    connectSSE();


</script>
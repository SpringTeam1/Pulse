<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section>
<div class="w-full flex gap-6">

    <!-- =============================== -->
    <!-- 🟦 LEFT : CREW NAVIGATION BAR -->
    <!-- =============================== -->
    <aside class="hidden md:flex flex-col w-56 bg-white border border-slate-100
                   rounded-2xl shadow-sm p-6 h-[800px] sticky top-24">

        <!-- 크루 이름 (REST로 불러와서 JS로 채움) -->
        <h2 id="crew-name" class="text-xl font-bold text-slate-900 mb-6">${crewName}</h2>

        <!-- 네비 메뉴 -->
        <nav class="flex flex-col gap-2">
            <a href="/pulse/crewdashboard?crewSeq=${crewSeq}"
               class="px-3 py-2 rounded-lg text-slate-700 hover:bg-slate-100 font-medium">
                홈
            </a>

            <a href="/pulse/crewboard/list?crewSeq=${crewSeq}"
               class="px-3 py-2 rounded-lg text-slate-700 hover:bg-slate-100 font-medium">
                게시판
            </a>

            <a href="/pulse/crewmembers?crewSeq=${crewSeq}"
               class="px-3 py-2 rounded-lg text-slate-700 hover:bg-slate-100 font-medium">
                멤버
            </a>

            <a href="/pulse/crewschedule?crewSeq=${crewSeq}"
               class="px-3 py-2 rounded-lg text-slate-700 hover:bg-slate-100 font-medium">
                일정
            </a>

            <a href="/pulse/crewgallery?crewSeq=${crewSeq}"
               class="px-3 py-2 rounded-lg text-slate-700 hover:bg-slate-100 font-medium">
                사진첩
            </a>

            <!-- 리더 전용 -->
            <c:if test="${isLeader}">
                <div id="leader-menu">
                    <div class="mt-4 pt-4 border-t border-slate-200"></div>

                    <a href="/pulse/crewrequest?crewSeq=${crewSeq}"
                       class="px-3 py-2 rounded-lg text-slate-700 hover:bg-slate-100 font-medium">
                        가입 요청 관리
                    </a>
                </div>
            </c:if>
        </nav>
    </aside>


    <!-- =============================== -->
    <!-- 🟩 CENTER : MAIN DASHBOARD CONTENT -->
    <!-- =============================== -->
    <main class="flex-1 flex flex-col gap-8">

        <!-- 🔹 1. 상단 2개 카드 (크루 대표사진 / 게시글) -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">


            <div class="bg-white rounded-2xl shadow-sm border border-slate-100 p-6 flex flex-col gap-4">
                <div>
                    <p class="text-xs text-slate-400 uppercase">Crew</p>
                    <h2 class="mt-1 text-lg font-semibold text-slate-900">대표이미지</h2>
                </div>

                <div class="mt-4">
                    <div class="aspect-video bg-slate-100 rounded-xl overflow-hidden">
                        <img id="crew-image"
                             src="${pageContext.request.contextPath}/crewmainFile/${attach}"
                             alt="Crew Image"
                             class="w-full h-full object-cover">
                    </div>
                </div>
            </div>

            <!-- 게시글 현황 카드 -->
            <div class="bg-white rounded-2xl shadow-sm border border-slate-100 p-6 flex flex-col gap-4">
                <div class="flex justify-between">
                    <div>
                        <p class="text-xs text-slate-400 uppercase">Board Overview</p>
                        <h2 class="mt-1 text-lg font-semibold text-slate-900">게시글 현황</h2>
                    </div>
                    <span class="px-2.5 py-1 rounded-full text-xs mt-4 bg-slate-100 text-slate-600">이번 주</span>
                </div>

                <div class="grid grid-cols-2 gap-4 mt-1">
                    <div>
                        <p class="text-xs text-slate-400">총 게시글 수</p>
                        <p id="post-total" class="text-2xl font-bold text-slate-900">-</p>
                    </div>
                    <div>
                        <p class="text-xs text-slate-400">이번주 작성된 글</p>
                        <p id="post-weekly" class="text-2xl font-bold text-slate-900">-</p>
                    </div>
                </div>

                <!-- 조회수 TOP -->
                <div class="mt-3 p-4 rounded-xl bg-slate-50 flex justify-between items-start">
                    <div class="min-w-0">
                        <p class="text-[11px] text-slate-500 uppercase mb-1">조회수 Top</p>
                        <p id="post-topview-title" class="text-sm font-semibold text-slate-900 truncate">-</p>
                        <p id="post-topview-writer" class="text-xs text-slate-500 truncate">-</p>
                    </div>
                    <div class="text-sm text-slate-600">
                        👁 <span id="post-topview-count">0</span>
                    </div>
                </div>
            </div>

        </div>

        <!-- 🔹 하트 많은 게시글 -->
        <div class="bg-white rounded-2xl shadow-sm border border-slate-100 p-6">
            <p class="text-xs text-slate-400 uppercase mb-1">Most Liked Post</p>
            <h2 class="text-lg font-semibold text-slate-900 mb-3">하트가 많은 게시글</h2>

            <div class="p-4 bg-slate-50 rounded-xl flex justify-between items-center">
                <div class="min-w-0">
                    <p id="post-mostlike-title" class="font-semibold text-slate-900 truncate">-</p>
                    <p id="post-mostlike-writer" class="text-xs text-slate-500 truncate">-</p>
                </div>
                <div class="text-rose-500 text-xl font-bold">
                    ❤️ <span id="post-mostlike-count">0</span>
                </div>
            </div>
        </div>

        <!-- 🔹 진행중인 러너 대회 정보 -->
        <div class="bg-white rounded-2xl shadow-sm border border-slate-100 p-6">
            <p class="text-xs text-slate-400 uppercase mb-1">Runner Event</p>
            <h2 class="text-lg font-semibold text-slate-900 mb-4">진행 중인 마라톤 대회</h2>

            <div class="p-4 bg-slate-50 rounded-xl">
                <p id="event-name" class="font-semibold text-slate-900">-</p>
                <p id="event-date" class="text-xs text-slate-500 mt-1">-</p>
                <p id="event-location" class="text-xs text-slate-500 mt-1">-</p>
            </div>
        </div>

    </main>


    <!-- =============================== -->
    <!-- 🟧 RIGHT : SSE REALTIME CHAT -->
    <!-- =============================== -->
    <aside class="hidden lg:flex flex-col w-80 bg-white border border-slate-100
                   rounded-2xl shadow-sm h-[800px] p-6">

        <h2 class="text-lg font-semibold text-slate-900 mb-4">채팅</h2>

        <div id="chat-box" class="flex-1 overflow-y-auto pr-2 space-y-3 text-sm"></div>

        <div class="mt-4">
            <input id="chat-input"
                   type="text"
                   placeholder="메시지 입력"
                   class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-slate-400" />
        </div>

    </aside>

</div>
</section>
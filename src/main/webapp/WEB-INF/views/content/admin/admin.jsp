<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 메인 섹션 -->
<section class="max-w-6xl mx-auto mt-10 space-y-10">

	<!-- 페이지 타이틀 -->
	<header class="text-center">
		<h1 class="text-4xl font-bold text-brand">📊 관리자 대시보드</h1>
		<p class="text-gray-600 mt-2">사이트 활동, 커뮤니티 상태, 회원 통계를 한눈에 확인하세요.</p>
	</header>

	<!-- 카드 레이아웃 (3열 반응형) -->
	<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

		<!-- 🔥 러닝 활동 -->
		<div class="bg-white rounded-xl shadow p-6 flex flex-col">
			<h2 class="text-2xl font-bold text-brand mb-4">🔥 러닝 활동</h2>

			<div>
				<h3 class="text-lg font-semibold text-gray-700 mb-2">오늘의 인기 코스
					TOP3</h3>
				<ul class="list-disc list-inside text-gray-600 space-y-1">
					<c:forEach items="${top3Courses}" var="dto">
						<li><a
							href="/alldayrun/course/courseMain.do?courseSeq=${dto.courseSeq}"
							class="text-brand hover:text-brand-dark font-medium transition">
								${dto.courseName} </a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="mt-6">
				<h3 class="text-lg font-semibold text-gray-700 mb-3">챌린지별 참여율</h3>
				<div class="chart-container w-full h-60">
					<canvas id="challengeChart"></canvas>
				</div>
			</div>
		</div>

		<!-- 💬 커뮤니티 관리 -->
		<div class="bg-white rounded-xl shadow p-6 flex flex-col">
			<h2 class="text-2xl font-bold text-brand mb-4">💬 커뮤니티 관리</h2>

			<!-- 미처리 리스트 -->
			<div>
				<h3 class="text-lg font-semibold text-gray-700 mb-2">미처리 리스트</h3>
				<c:set var="suggestionCount" value="0" />
				<ul class="space-y-1 text-gray-600">
					<li>🎯 <a href="#"
						class="text-brand hover:text-brand-dark font-medium">신고</a> : 1
					</li>
					<li>❓ <a href="#"
						class="text-brand hover:text-brand-dark font-medium">문의</a> : 13
					</li>

					<c:forEach items="${list}" var="dto">
						<c:if test="${dto.boardContentSeq != null}">
							<c:set var="suggestionCount" value="${suggestionCount + 1}" />
						</c:if>
					</c:forEach>
					<li>📜 <a href="#"
						class="text-brand hover:text-brand-dark font-medium"> 건의 </a> :
						${suggestionCount}
					</li>
				</ul>
			</div>

			<!-- 승인 대기 현황 -->
			<div class="mt-6">
				<h3 class="text-lg font-semibold text-gray-700 mb-2">승인 대기 현황</h3>

				<c:set var="pendingCount" value="0" />
				<c:forEach items="${list}" var="dto">
					<c:if test="${dto.courseApproval == '대기'}">
						<c:set var="pendingCount" value="${pendingCount + 1}" />
					</c:if>
				</c:forEach>

				<ul class="space-y-1 text-gray-600">
					<li>👥 <a href="#"
						class="text-brand hover:text-brand-dark font-medium">크루</a> : 3
					</li>
					<li>📍 <a href="#"
						class="text-brand hover:text-brand-dark font-medium"> 코스 </a> :
						${pendingCount}
					</li>
				</ul>
			</div>
		</div>

		<!-- 🌏 회원 통계 -->
		<div
			class="bg-white rounded-xl shadow p-6 flex flex-col md:col-span-2 lg:col-span-1">
			<h2 class="text-2xl font-bold text-brand mb-4">🌏 회원 통계</h2>
			<h3 class="text-lg font-semibold text-gray-700 mb-3">등급별 회원 평균
				거리</h3>
			<div class="chart-container w-full h-64">
				<canvas id="memberChart"></canvas>
			</div>
		</div>

	</div>
</section>
























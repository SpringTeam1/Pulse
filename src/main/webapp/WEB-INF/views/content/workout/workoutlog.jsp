<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="max-w-7xl mx-auto p-6 space-y-8">
	<h2
		class="text-3xl font-bold text-gray-900 border-b border-gray-200 pb-4">내
		운동 기록</h2>

	<div class="bg-white shadow-lg rounded-lg overflow-hidden">
		<table class="min-w-full divide-y divide-gray-200">
			<thead class="bg-gray-50">
				<tr>
					<th scope="col"
						class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
						운동 날짜</th>
					<th scope="col"
						class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
						총 거리</th>
					<th scope="col"
						class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
						운동 시간</th>
					<th scope="col"
						class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
						평균 페이스</th>
					<th scope="col"
						class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
						소모 칼로리</th>
				</tr>
			</thead>

			<tbody class="bg-white divide-y divide-gray-200">
				<c:choose>
					<c:when test="${not empty activityLogs}">
						<c:forEach var="log" items="${activityLogs}">
							<tr class="hover:bg-gray-50 cursor-pointer clickable-row"
                    data-href="<c:url value='/workout/detail/${log.workoutSeq}' />">
								<td
									class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
									${log.workoutDate}</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
									<fmt:formatNumber value="${log.totalDistance}" pattern="0.00" />
									km
								</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
									<fmt:formatNumber value="${log.totalTime / 60}" pattern="0" />
									분
								</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
									<fmt:formatNumber value="${log.avgPace}" pattern="0.00" />
									분/km
								</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
									<fmt:formatNumber value="${log.totalCalories}" type="number" />
									kcal
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="px-6 py-12 text-center text-gray-500">
								아직 등록된 운동 기록이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>

</div>
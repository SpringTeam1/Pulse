<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<section class="max-w-5xl mx-auto mt-10 bg-white rounded-2xl shadow p-8 space-y-6">

    <!-- 🏞 대표 배너 이미지 (비율 자동 맞춤형) -->
    <div class="w-full rounded-2xl overflow-hidden shadow bg-gray-100 flex items-center justify-center">
        <img src="${pageContext.request.contextPath}/crewmainFile/${empty dto.crewAttach ? 'default.jpg' : dto.crewAttach}"
             alt="${dto.crewName} 대표 이미지"
             class="w-full h-auto object-contain"
             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/crewmainFile/default.jpg';" />
    </div>

    <!-- 🎯 크루 정보 -->
    <div class="space-y-4">
        <div class="flex items-center justify-between">
            <h1 class="text-4xl font-bold text-gray-900">${dto.crewName}</h1>

            <c:choose>
                <c:when test="${isUserInCrew}">
                    <%-- 이미 가입된 유저: 비활성 버튼 --%>
                    <button class="px-5 py-3 bg-gray-300 text-gray-600 rounded-lg cursor-not-allowed font-semibold"
                            title="이미 가입된 크루입니다" disabled>
                        🏃 가입완료
                    </button>
                </c:when>

                <c:otherwise>
                    <button
                            class="px-5 py-3 bg-brand text-white rounded-lg hover:bg-brand-dark transition font-semibold"
                            onclick="joinCrew('${dto.crewSeq}')">
                        🏃‍♂️ 가입신청
                    </button>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="text-gray-500 space-y-1">
            <p>
                ${dto.regionCity} ${dto.regionCounty} ${dto.regionDistrict} | ${dto.memberCount}명 | ${dto.nickname}
            </p>

        </div>

        <div class="mt-4 text-gray-700 leading-relaxed border-t pt-4">
            <p>${dto.description}</p>
        </div>
    </div>

    <!-- 📸 활동 사진 섹션 -->
    <div class="mt-8">
        <h2 class="text-2xl font-bold text-gray-800 border-b pb-2 mb-4">크루 활동 사진</h2>

        <c:choose>
            <c:when test="${not empty photoList}">
                <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4">
                    <c:forEach var="photo" items="${photoList}">
                        <div class="border rounded-lg overflow-hidden shadow-sm hover:shadow-md transition bg-gray-50 flex items-center justify-center">
                            <img src="${pageContext.request.contextPath}/crewboardFile/${photo.attach}"
                                 alt="${photo.title}"
                                 class="w-full h-auto object-contain"
                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/crewmainFile/default.jpg';" />
                        </div>
                    </c:forEach>
                </div>
            </c:when>

            <c:otherwise>
                <div class="text-center text-gray-400 italic border border-gray-200 rounded-lg py-8">
                    등록된 활동 사진이 없습니다 📷
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</section>

<!-- ✅ CSS (Pulse 브랜드 컬러 보조 정의) -->
<style>
    .bg-brand {
        background-color: #1DA1F2; /* Pulse 메인 컬러 */
    }
    .hover\:bg-brand-dark:hover {
        background-color: #155FA0;
    }
</style>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-4xl mx-auto mt-12 bg-white rounded-2xl shadow p-10 space-y-8">

    <!-- Title -->
    <div class="space-y-3">
        <h1 class="text-4xl font-extrabold text-gray-900 leading-tight">${dto.title}</h1>

        <div class="flex flex-wrap justify-between items-center text-sm text-gray-500">

            <!-- 작성자 -->
            <div class="flex items-center gap-2">
                <span class="text-black px-2">${dto.adto.nickname}</span>
            </div>

            <!-- 작성일 / 조회수 / 좋아요 -->
            <div class="flex items-center gap-4">
                <span>${dto.regdate}</span>
                <span>👁️ ${dto.readCount}</span>
                <span id="like-count">❤️ ${dto.favoriteCount}</span>
            </div>

        </div>
    </div>

    <!-- Content -->
    <div class="text-gray-800 text-[15px] leading-relaxed whitespace-pre-wrap">
        ${dto.content}
    </div>

    <!-- Attachment -->
    <c:if test="${not empty dto.attach}">
        <c:choose>

            <%-- 이미지 파일일 경우 --%>
            <c:when test="${fileExt eq 'jpg' or fileExt eq 'jpeg' or fileExt eq 'png' or fileExt eq 'gif'}">
                <div class="mt-8">
                    <img src="${pageContext.request.contextPath}/boardsuggestion/${dto.attach}"
                         alt="첨부 이미지"
                         class="w-full max-h-[500px] object-contain rounded-xl border border-gray-200 shadow-sm"/>
                </div>
            </c:when>

            <%-- 일반 파일 --%>
            <c:otherwise>
                <div class="bg-gray-50 rounded-xl p-5 shadow-sm mt-8">
                    <div class="flex items-center gap-2 text-gray-700 font-semibold mb-2">
                        <span>📎</span> <span>첨부파일</span>
                    </div>

                    <a href="${pageContext.request.contextPath}/boardsuggestion/${dto.attach}"
                       download="${dto.attach}"
                       class="inline-flex items-center px-4 py-2 text-sm rounded-lg bg-gray-100 hover:bg-brand hover:text-white transition">
                        ${dto.attach}
                    </a>
                </div>
            </c:otherwise>

        </c:choose>
    </c:if>

    <!-- Buttons -->
    <div class="flex justify-between items-center pt-4">

        <!-- 좋아요 (추후 기능 연결 가능) -->
        <img id="like-img"
             src="${pageContext.request.contextPath}/resources/img/heart.png"
             alt="좋아요"
             class="w-5 h-5 cursor-pointer transition hover:scale-110"
             data-board="${dto.boardContentSeq}" />

        <div class="flex gap-2">

            <!-- 목록 -->
            <button type="button"
                    onclick="location.href='${pageContext.request.contextPath}/boardsuggestion/list'"
                    class="px-3 py-1.5 rounded-lg bg-gray-100 hover:bg-gray-200 text-gray-700 text-sm font-medium transition">
                목록
            </button>

            <!-- 수정 -->
            <button type="button"
                    onclick="location.href='${pageContext.request.contextPath}/boardsuggestion/edit?boardContentSeq=${dto.boardContentSeq}'"
                    class="px-3 py-1.5 rounded-lg bg-brand text-white text-sm font-medium hover:bg-brand-dark shadow-sm hover:shadow-md transition">
                수정
            </button>

            <!-- 삭제 -->
            <button type="button" id="btn-del" data-seq="${dto.boardContentSeq}"
                    class="px-3 py-1.5 rounded-lg bg-red-500 text-white text-sm font-medium hover:bg-red-600 shadow-sm hover:shadow-md transition">
                삭제
            </button>

        </div>
    </div>

</section>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="max-w-4xl mx-auto mt-12 bg-white rounded-2xl shadow p-10 space-y-8">

    <!-- Title -->
    <div class="space-y-3">
        <h1 class="text-4xl font-extrabold text-gray-900 leading-tight">${board.title}</h1>
        <div class="flex flex-wrap justify-between items-center text-sm text-gray-500">
            <div class="flex items-center gap-2">
                <span class="text-black px-2">${board.nickname}</span>
            </div>
            <div class="flex items-center gap-4">
                <span>${board.regdate}</span>
                <span>👁️ ${board.readCount}</span>
                <span>❤️ ${board.favoriteCount}</span>
            </div>
        </div>
    </div>

    <!-- Content -->
    <div class="text-gray-800 text-[15px] leading-relaxed whitespace-pre-wrap">
        ${board.content}
    </div>

    <!-- Attachment -->
    <c:if test="${not empty board.attach}">
        <c:choose>

            <c:when test="${fileExt eq 'jpg' or fileExt eq 'jpeg' or fileExt eq 'png' or fileExt eq 'gif'}">
                <div class="mt-8">
                    <img src="${pageContext.request.contextPath}/crewboardFile/${board.attach}"
                         alt="첨부 이미지"
                         class="w-full max-h-[500px] object-contain rounded-xl border border-gray-200 shadow-sm" />
                </div>
            </c:when>


            <c:otherwise>
                <div class="bg-gray-50 rounded-xl p-5 shadow-sm mt-8">
                    <div class="flex items-center gap-2 text-gray-700 font-semibold mb-2">
                        <span>📎</span> <span>첨부파일</span>
                    </div>
                    <a href="${pageContext.request.contextPath}/crewboardFile/${board.attach}"
                       download="${board.attach}"
                       class="inline-flex items-center px-4 py-2 text-sm rounded-lg bg-gray-100 hover:bg-brand hover:text-white transition">
                            ${board.attach}
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <!-- Buttons -->
    <div class="flex justify-end gap-3 pt-4">
        <button type="button"
                class="px-5 py-2.5 rounded-full bg-red-100 hover:bg-red-400 text-red-700 font-medium transition">
            <img src="${pageContext.request.contextPath}/crewboardFile/heart.png" alt="하트">
        </button>
        <button type="button"
                onclick="location.href='${pageContext.request.contextPath}/crewboard/list?crewSeq=${board.crewSeq}'"
                class="px-5 py-2.5 rounded-lg bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium transition">
            목록으로
        </button>

        <button type="button"
                onclick="location.href='${pageContext.request.contextPath}/crewboard/edit?boardContentSeq=${board.boardContentSeq}'"
                class="px-5 py-2.5 rounded-lg bg-brand text-white font-medium hover:bg-brand-dark shadow-md hover:shadow-lg transition">
            수정하기
        </button>
    </div>

</section>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        console.log("📄 crewboard.view.jsp loaded:", "${board.title}");
    });
</script>

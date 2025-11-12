<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <span id="like-count">❤️ ${board.favoriteCount}</span>
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
                         class="w-full max-h-[500px] object-contain rounded-xl border border-gray-200 shadow-sm"/>
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
        <img id="like-img"
             src="${pageContext.request.contextPath}/crewboardFile/heart.png"
             alt="좋아요"
             class="w-8 h-8 cursor-pointer transition hover:scale-110"
             data-board="${board.boardContentSeq}" />
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

    document.getElementById("like-img").addEventListener("click", async (e) => {
        const img = e.currentTarget;
        const boardContentSeq = img.dataset.board;

        if (img.dataset.liked === "true") return;

        try {
            const res = await fetch(`${pageContext.request.contextPath}/api/v1/crew/board/\${boardContentSeq}/like`, {
                method: "POST",
            });
            const data = await res.json();
            if (data.success) {
                img.style.filter = "invert(30%) sepia(100%) saturate(6000%) hue-rotate(-10deg) brightness(1.1)";
                // ↑ 대략 빨간색 톤으로 하트 채색
                img.dataset.liked = "true";

                console.log("📡 서버 응답:", data);
                const countEl = document.getElementById("like-count");
                countEl.textContent = `❤️ \${data.favoriteCount}`;
            }
        } catch (err) {
            console.error(err);
            alert("서버 오류가 발생했습니다.");
        }
    });


</script>

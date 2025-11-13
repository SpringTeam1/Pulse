<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="max-w-6xl mx-auto mt-10 bg-white rounded-xl shadow p-8 space-y-8">

    <!-- 제목 -->
    <div class="flex flex-col sm:flex-row justify-between items-center">
        <h1 class="text-3xl font-bold text-brand">📢 공지사항게시판</h1>
    </div>

    <p class="text-gray-600 text-sm">전체 공지사항을 확인할 수 있습니다.</p>
	
	<c:set var="num" value="${fn:length(list)}" />
    <!-- 리스트 테이블 -->
    <div class="block overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-left border-collapse">
            <thead class="bg-gray-50 text-gray-600 text-sm uppercase font-semibold">
            <tr>
                <th class="px-6 py-3 text-center w-12">NO.</th>
                <th class="px-6 py-3">제목</th>
                <th class="px-6 py-3 w-32 text-center">작성자</th>
                <th class="px-6 py-3 w-36 text-center">작성일</th>
                <th class="px-6 py-3 w-24 text-center">조회수</th>
            </tr>
            </thead>

            <!-- 🔵 여기서 JSTL로 데이터 출력 -->
            <tbody class="text-gray-700 text-sm divide-y divide-gray-100">

            <c:if test="${empty list}">
                <tr>
                    <td colspan="5" class="text-center text-gray-400 py-6">
                        게시글이 없습니다.
                    </td>
                </tr>
            </c:if>

            <c:forEach items="${list}" var="bndto">
                <tr class="hover:bg-gray-50 cursor-pointer"
                    onclick="location.href='${pageContext.request.contextPath}/boardnotice/view.do?seq=${bndto.boardSeq}'">
                    <td class="px-6 py-3 text-center">${num}</td>
                    <td class="px-6 py-3">${bndto.title}</td>
                    <td class="px-6 py-3 text-center">${bndto.writer}</td>
                    <td class="px-6 py-3 text-center">${bndto.regdate}</td>
                    <td class="px-6 py-3 text-center">${bndto.readCount}</td>
                </tr>
                
                <!-- 다음 번호 -->
                <c:set var="num" value="${num - 1}"></c:set>
                
            </c:forEach>

            </tbody>
        </table>
    </div>

    <div class="flex justify-end">
        <a href="${pageContext.request.contextPath}/boardnotice/add.do"
           class="px-4 py-2 bg-brand text-white font-semibold rounded-lg hover:bg-brand-dark transition">
            ✏️ 새 글 작성
        </a>
    </div>

</section>

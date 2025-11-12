<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>Pulse</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="${pageContext.request.contextPath}/asset/css/output.css" rel="stylesheet">
</head>

<body class="bg-gray-50 font-display">

  <!-- ✅ 공통 헤더 -->
  <tiles:insertAttribute name="header" />

  <!-- ✅ 메인 콘텐츠 -->
  <main class="container mx-auto p-6">
    <tiles:insertAttribute name="content" />
  </main>

  <!-- ✅ 공통 스크립트 (예: 공용 기능 js) -->
  <script src="<c:url value='/asset/javascript/common.js' />"></script>

  <!-- ✅ 페이지별 추가 스크립트 -->
  <tiles:insertAttribute name="script" ignore="true" />
</body>
</html>

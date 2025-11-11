<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form id="registerForm" 
    enctype="multipart/form-data"
    data-api-url="<c:url value='/api/course/gpx' />" 
    data-redirect-url="<c:url value='/' />">
    코스 이름: <input type="text" id="courseName" name="courseName" value="테스트1"><br>
    코스 설명: <textarea id="description" name="description">테스트1</textarea><br>
    작성자 ID: <input type="text" id="accountId" name="accountId" value="hong"><br>
    GPX 파일: <input type="file" id="gpxFile" name="gpxFile" accept=".gpx"><br>

    <button type="button" id="submitBtn">코스 등록 요청하기</button>
</form>

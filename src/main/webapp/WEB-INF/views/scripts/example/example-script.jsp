<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>

/* 여기에 javascript 내용 직접 작성 혹은 .js파일 연결짓기  */

  document.addEventListener("DOMContentLoaded", () => {
    const btn = document.getElementById("alertBtn");
    if (btn) {
      btn.addEventListener("click", () => alert("example.jsp에서 JS 실행됨 ✅"));
    }
  });

</script>
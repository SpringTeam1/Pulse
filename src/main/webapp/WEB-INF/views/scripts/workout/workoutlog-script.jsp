<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e002eba63b35b03451ac4917b7108dd6&autoload=false&libraries=services"></script>
<script>
    // $(document).ready(function() { ... }); 와 동일
    $(function() {
        $(".clickable-row").on("click", function() {
            // data-href 속성에 저장된 URL을 가져옵니다.
            const href = $(this).data("href");
            
            if (href) {
                // 해당 URL로 페이지를 이동시킵니다.
                window.location.href = href;
            }
        });
    });
</script>
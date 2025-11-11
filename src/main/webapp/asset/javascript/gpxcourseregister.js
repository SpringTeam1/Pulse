$(function() {

    const registerForm = $('#gpxRegisterForm');
    const submitBtn = $('#gpxSubmitBtn');

    if (submitBtn.length) {

        // (핵심) 폼 태그의 data- 속성에서 URL들을 미리 읽어옵니다.
        // jQuery의 .data() 메서드를 사용합니다.
        const apiUrl = registerForm.data('apiUrl'); // data-api-url
        const redirectUrl = registerForm.data('redirectUrl'); // data-redirect-url

        submitBtn.on('click', function() {

            const formData = new FormData(registerForm[0]); // 폼 객체[0] = DOM 요소

            if (!$('#gpxFile').val()) {
                 alert('GPX 파일을 첨부해주세요.');
                 return;
            }

            $.ajax({
                // (수정) 전역 변수 대신 data-*에서 읽어온 변수 사용
                url: apiUrl, 
                type: 'POST',
                data: formData,
                processData: false, 
                contentType: false, 

                success: function(savedCourse) {
                    alert('코스 등록이 요청되었습니다. 관리자 승인 후 반영됩니다.');
                    // (수정) 전역 변수 대신 data-*에서 읽어온 변수 사용
                    location.href = "/pulse/course/register"; 
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                    alert('등록 요청에 실패했습니다.');
                }
            });
        });
    }
});
$(function() {

    const recordForm = $('#gpxRecordForm');
    const submitBtn = $('#gpxSubmitBtn');

    if (submitBtn.length) {

        // (핵심) 폼 태그의 data- 속성에서 URL들을 미리 읽어옵니다.
        // jQuery의 .data() 메서드를 사용합니다.
        const apiUrl = recordForm.data('apiUrl'); // data-api-url
        const redirectUrl = recordForm.data('redirectUrl'); // data-redirect-url

        submitBtn.on('click', function() {
            $('#courseName-error').addClass('hidden').text('');
            $('#courseName').removeClass('border-red-500');
            $('#gpxFile-error').addClass('hidden').text('');
            $('#gpx-error').addClass('hidden').text('');
            $('#weight-error').addClass('hidden').text('');
            // --- (수정) 유효성 검사 (Validation) ---
            
            // 3-2. GPX 파일 검사 (필수)
            if ($('#gpxFiles').val() === '') { //
                 $('#gpx-error').text('GPX 파일을 하나 이상 첨부해주세요.').removeClass('hidden');
                 return;
            }

            // 3-3. 체중 검사 (필수, 숫자)
            const userWeight = $('#userWeight').val(); //
            if (!userWeight) {
                $('#weight-error').text('칼로리 계산을 위해 체중을 입력해주세요.').removeClass('hidden');
                $('#userWeight').focus();
                return;
            }
            if (isNaN(userWeight) || userWeight <= 0) {
                $('#weight-error').text('유효한 체중(숫자)을 입력해주세요.').removeClass('hidden');
                $('#userWeight').focus();
                return;
            }

            const formData = new FormData(recordForm[0]); // 폼 객체[0] = DOM 요소
            
            $.ajax({
                // (수정) 전역 변수 대신 data-*에서 읽어온 변수 사용
                url: apiUrl, 
                type: 'POST',
                data: formData,
                processData: false, 
                contentType: false, 

                success: function(savedLogs) {
                    alert(`운동 기록 ${savedLogs.length}개가 성공적으로 저장되었습니다.`);
                    // (수정) 전역 변수 대신 data-*에서 읽어온 변수 사용
                    location.href = redirectUrl; 
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                    if (xhr.status === 401) {
                         alert('로그인이 필요합니다.');
                    } else {
                         alert('기록 저장에 실패했습니다. (서버 오류)');
                    }
                }
            });
        });
    }
});
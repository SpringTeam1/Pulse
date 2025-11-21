<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<script>
	
	document.addEventListener('DOMContentLoaded', () => {
	
		// ===== 이미지 미리보기 + 확장자 검증 =====
		const photo   = document.getElementById('photo');
		const preview = document.getElementById('preview');
	
		if (photo && preview) {
			photo.addEventListener('change', e=>{
		    const f = e.target.files && e.target.files[0];
		    if(!f) return;
		    const ok = /(png|jpg|jpeg)$/i.test(f.name);
		    if(!ok){ alert('png, jpg, jpeg만 업로드 가능합니다.'); photo.value=''; return; }
		    preview.src = URL.createObjectURL(f);
		    
		    const hidden = document.getElementById('photoName');
		    if (hidden) hidden.value = f.name;
			});
		}
		
		// ===== 이미지 초기화 =====
		window.resetPhoto = function(){
		    const pvw = document.getElementById('preview');
		    const input = document.getElementById('photo');
		    const hidden = document.getElementById('photoName');
		    if (pvw)   pvw.src = '${pageContext.request.contextPath}/asset/pic/pic.png';
		    if (input) input.value = '';
		    if (hidden) hidden.value = 'pic.png';
		};
		
		// ===== 이름 유효성 =====
		(function(){
		    const form = document.getElementById('frmStep2');
		    if (!form) return;

		    const ko = /^[가-힣]{1,8}$/;
		    const en = /^[A-Za-z]{1,8}$/;

		    form.addEventListener('submit', function(e){
		      const nameEl = document.getElementById('name');
		      const name = nameEl ? nameEl.value.trim() : '';
		      const ok = ko.test(name) || en.test(name);
		      if (!ok) {
		        e.preventDefault();
		        let msg = document.querySelector('.name-msg');
		        if (!msg) {
		          msg = document.createElement('div');
		          msg.className = 'msg name-msg';
		          form.querySelector('.section .sec-title + .row')?.parentNode?.appendChild(msg);
		        }
		        if (msg) msg.textContent = '사용할 수 없는 이름입니다.';
		      }
		    });
		})();

		// ===== 생년월일(yyyy, mm, dd) =====
		(function(){
			const yyyy = document.getElementById('yyyy');
			const mm   = document.getElementById('mm');
			const dd   = document.getElementById('dd');
			if (!yyyy || !mm || !dd) return;

			const yNow = new Date().getFullYear();
			for(let y = yNow; y >= 1900; y--) {
			    yyyy.add(new Option(y, y));
			}
			for(let m = 1; m <= 12; m++) {
			    mm.add(new Option(m, m));
			}

			function fillDays(selectedDay) {
			    dd.length = 0;
			    const y = parseInt(yyyy.value, 10);
			    const m = parseInt(mm.value, 10);
			    if (!y || !m) return;
			    
			    const last = new Date(y, m, 0).getDate();
			    for(let d = 1; d <= last; d++) {
			      dd.add(new Option(d, d));
			    }

			    if (selectedDay && selectedDay <= last) {
			      dd.value = selectedDay;
			    } else {
			      dd.value = last;
			    }
			}

			yyyy.addEventListener('change', function() {
			    const selectedDay = parseInt(dd.value, 10);
			    fillDays(selectedDay);
			});

			mm.addEventListener('change', function() {
			    const selectedDay = parseInt(dd.value, 10);
			    fillDays(selectedDay);
			});

			const hiddenYear  = document.getElementById('hiddenYear');
			const hiddenMonth = document.getElementById('hiddenMonth');
			const hiddenDay   = document.getElementById('hiddenDay');

			if (hiddenYear && hiddenMonth && hiddenDay) {
			    yyyy.value = parseInt(hiddenYear.value, 10);
			    mm.value = parseInt(hiddenMonth.value, 10);
			    fillDays(parseInt(hiddenDay.value, 10));
			} else {
			    // fallback: 오늘 날짜
			    const today = new Date();
			    yyyy.value = today.getFullYear();
			    mm.value = today.getMonth() + 1;
			    fillDays(today.getDate());
			}

		})();
	
	});
	
	</script>

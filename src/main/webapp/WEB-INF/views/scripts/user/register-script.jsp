<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<script>
	
	//이미지 미리보기 + 확장자 검증
	const photo = document.getElementById('photo');
	const preview = document.getElementById('preview');
	photo.addEventListener('change', e=>{
	  const f = e.target.files[0];
	  if(!f) return;
	  const ok = /(png|jpg|jpeg)$/i.test(f.name);
	  if(!ok){ alert('png, jpg, jpeg만 업로드 가능합니다.'); photo.value=''; return; }
	  const url = URL.createObjectURL(f);
	  preview.src = url;
	});
	
	
	//이미지 초기화
	function resetPhoto() {
		  const preview = document.getElementById('preview');
		  const input = document.getElementById('photo');
		  const hidden = document.getElementById('photoName');

		  preview.src = '${pageContext.request.contextPath}/asset/pic/pic.png';
		  input.value = '';
		  hidden.value = 'pic.png';
		}
	
	
	//이름 유효성
	(function(){
	  var ko = /^[가-힣]{1,8}$/;
	  var en = /^[A-Za-z]{1,8}$/;

	  var form = document.getElementById('frmStep2');
	  if (!form) return;

	  form.addEventListener('submit', function(e){
	    var name = document.getElementById('name').value.trim(); // 변경된 부분

	    var ok = ko.test(name) || en.test(name);

	    if (!ok) {
	      e.preventDefault();

	      var msg = document.querySelector('.name-msg');
	      if (!msg) {
	        var div = document.createElement('div');
	        div.className = 'msg name-msg';
	        div.textContent = '사용할 수 없는 이름입니다.';
	        form.querySelector('.section .sec-title + .row').parentNode.appendChild(div);
	      } else {
	        msg.textContent = '사용할 수 없는 이름입니다.';
	      }
	    }
	  });
	})();
	
	
	//닉네임 중복 검사
	(function(){
	  const ctx = '${pageContext.request.contextPath}';
	  const csrfHeader = '${_csrf.headerName}';
	  const csrfToken  = '${_csrf.token}';

	  const btn  = document.getElementById('btnNick');
	  const nick = document.getElementById('nickname');
	  const msg  = document.getElementById('nickMsg');

	  if(!btn || !nick || !msg) return;

	  btn.addEventListener('click', async function(){
	    const value = nick.value.trim();
	    const rule = /^(?=.*[가-힣A-Za-z0-9])[가-힣A-Za-z0-9]{1,12}$/;

	    if (!rule.test(value)) {
	      msg.textContent = '닉네임 형식을 확인해주세요.';
	      msg.style.color = '#b00020';
	      return;
	    }

	    msg.textContent = '검사 중...';
	    msg.style.color = '#666';

	    try {
	      const res = await fetch(ctx + '/api/nick/check', {
	        method: 'POST',
	        headers: {
	          'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
	          [csrfHeader]: csrfToken
	        },
	        body: new URLSearchParams({ nickname: value }).toString()
	      });

	      if (!res.ok) throw new Error(res.status);
	      const r = await res.json();

	      if (r && r.duplicate === true) {
	        msg.textContent = '이미 존재하는 닉네임입니다.';
	        msg.style.color = '#b00020';
	      } else if (r && r.duplicate === false) {
	        msg.textContent = '사용 가능한 닉네임입니다.';
	        msg.style.color = '#188038';
	      } else {
	        msg.textContent = '응답 형식이 올바르지 않습니다.';
	        msg.style.color = '#b00020';
	      }
	    } catch(e){
	      msg.textContent = '중복 검사 실패 (' + e.message + ')';
	      msg.style.color = '#b00020';
	    }
	  });
	})();
	
	
	//생년월일
	(function(){
	  const yyyy = document.getElementById('yyyy'), mm=document.getElementById('mm'), dd=document.getElementById('dd');
	  const yNow = new Date().getFullYear();
	  for(let y=yNow; y>=1900; y--){ yyyy.add(new Option(y, y)); }
	  for(let m=1;m<=12;m++){ mm.add(new Option(m, m)); }
	  function fillDays(){
	    dd.length = 0;
	    const y = parseInt(yyyy.value), m = parseInt(mm.value);
	    if(!y||!m) return;
	    const last = new Date(y, m, 0).getDate();
	    for(let d=1; d<=last; d++){ dd.add(new Option(d, d)); }
	  }
	  yyyy.addEventListener('change', fillDays);
	  mm.addEventListener('change', fillDays);
	  // 초기값
	  yyyy.value = 2000; mm.value = 11; fillDays(); dd.value = 11;
	})();
	
	
	</script>



















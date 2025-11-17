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

	  //이메일 인증
	  const ctx    = '<c:out value="${pageContext.request.contextPath}"/>';
	  const token  = document.querySelector('meta[name="_csrf"]')?.content;
	  const header = document.querySelector('meta[name="_csrf_header"]')?.content;

	  function isEmail(v){
	    return /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(v);
	  }
	  async function postJson(url, data){
	    const headers = {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'};
	    if (header && token) headers[header] = token;

	    const body = new URLSearchParams();
	    Object.entries(data).forEach(([k,v]) => body.append(k, v));

	    const res = await fetch(url, {method:'POST', headers, body});
	    // 컨트롤러에서 Map 반환(@ResponseBody) -> JSON
	    if (!res.ok) throw new Error('HTTP ' + res.status);
	    return await res.json();
	  }

	  // ===== 발송 버튼 =====
	  const btnSend = document.getElementById('btnSend');
	  const emailEl = document.getElementById('accountId');
	  const msgEl   = document.getElementById('emailMsg');
	  const wrap    = document.getElementById('verifyWrap');

	  if (btnSend){
	    btnSend.addEventListener('click', async (e) => {
	      e.preventDefault();
	      const email = (emailEl?.value || '').trim();
	      if (!isEmail(email)){
	        msgEl?.classList.remove('ok'); msgEl?.classList.add('err');
	        if (msgEl) msgEl.textContent = '이메일 형식이 올바르지 않습니다.';
	        return;
	      }
	      btnSend.disabled = true;
	      if (msgEl){ msgEl.classList.remove('ok','err'); msgEl.textContent = '전송 중...'; }
	      try{
	        const res = await postJson(ctx + '/user/sendmail.do', {email});
	        if (res && res.result == 1){
	          msgEl?.classList.remove('err'); msgEl?.classList.add('ok');
	          if (msgEl) msgEl.textContent = '인증번호를 발송했습니다. 메일함을 확인하세요.';
	          wrap?.style && (wrap.style.display = '');
	        }else{
	          msgEl?.classList.remove('ok'); msgEl?.classList.add('err');
	          if (msgEl) msgEl.textContent = '메일 전송 실패';
	        }
	      }catch(err){
	        msgEl?.classList.remove('ok'); msgEl?.classList.add('err');
	        if (msgEl) msgEl.textContent = '통신 오류';
	      }finally{
	        btnSend.disabled = false;
	      }
	    });
	  }

	  // ===== 검증 버튼 =====
	  const btnVerify = document.getElementById('btnVerify');
	  const codeEl    = document.getElementById('code');
	  const hiddenOk  = document.getElementById('emailVerified');

	  if (btnVerify){
	    btnVerify.addEventListener('click', async () => {
	      const email = (emailEl?.value || '').trim();
	      const code  = (codeEl?.value || '').trim();
	      try{
	        const res = await postJson(ctx + '/user/verifycode.do', {email, code});
	        if (res && res.ok){
	          if (hiddenOk) hiddenOk.value = 'true';
	          msgEl?.classList.remove('err'); msgEl?.classList.add('ok');
	          if (msgEl) msgEl.textContent = '이메일 인증이 완료되었습니다.';
	        }else{
	          msgEl?.classList.remove('ok'); msgEl?.classList.add('err');
	          if (msgEl) msgEl.textContent = '인증 실패';
	        }
	      }catch(err){
	        msgEl?.classList.remove('ok'); msgEl?.classList.add('err');
	        if (msgEl) msgEl.textContent = '통신 오류';
	      }
	    });
	  }

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
	    for(let y=yNow; y>=1900; y--){ yyyy.add(new Option(y, y)); }
	    for(let m=1;m<=12;m++){ mm.add(new Option(m, m)); }

	    function fillDays(){
	      dd.length = 0;
	      const y = parseInt(yyyy.value, 10);
	      const m = parseInt(mm.value, 10);
	      if(!y || !m) return;
	      const last = new Date(y, m, 0).getDate();
	      for(let d=1; d<=last; d++){ dd.add(new Option(d, d)); }
	    }
	    yyyy.addEventListener('change', fillDays);
	    mm.addEventListener('change', fillDays);

	    // 초기값
	    yyyy.value = '2000';
	    mm.value   = '11';
	    fillDays();
	    dd.value   = '11';
	  })();

	});
	
	</script>




















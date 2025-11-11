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



















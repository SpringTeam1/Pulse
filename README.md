# Pulse
### Spring Legacy Project

dev 브랜치 변경사항(2025.11.11 18시 이후 변경된 내용)
1. tiles.xml에 definition 추가(script 유무에 따라 분리)
   - jsp에 javascript가 필요한 경우 controller에서 jsp 호출할때 `script.*.*` 식으로 호출 필요
2. tailwind forms 플러그인 추가
   - `npm install @tailwindcss/forms` 를 cmd창에서 실행
   - 나머지 파일도 추가되었는데 pull하면 됨
3. layout.jsp의 공통 javascript 경로가 우리 프로젝트와 다르고 common.js파일도 없어서 추가하였음
### Swagger UI
http://localhost:8080/pulse/swagger-ui.html

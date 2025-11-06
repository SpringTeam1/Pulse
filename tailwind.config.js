/** @type {import('tailwindcss').Config} */
module.exports = {
  // ⭐️ 이 부분이 가장 중요합니다.
  // JSP 파일이 있는 경로를 지정합니다.(tailwind의 스캔 대상)
  content: [
    "./src/main/webapp/WEB-INF/views/**/*.jsp",
    "./src/main/webapp/**/*.jsp" 
    // 혹시 다른 경로에도 JSP/HTML 파일이 있다면 추가
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
// tailwind.config.js (Cleaned)

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    // 이 패턴 하나면 /webapp/ 아래의 모든 .jsp 파일을 스캔합니다.
    // (/WEB-INF/views/inc/header.jsp 포함)
    "./src/main/webapp/**/*.jsp"
  ],

  theme: {
    extend: {
      colors: {
        brand: {
          DEFAULT: '#1DA1F2',
          dark: '#4338CA',
        },
      },
      fontFamily: {
        display: ['"Poppins"', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
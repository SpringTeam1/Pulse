<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- =====================================================================================================
  ✅ [example.jsp 가이드라인] (Tiles + Tailwind + 반응형 스타일 기준)
  - Tiles의 content 영역으로 삽입됩니다.
  - HTML <html>, <head>, <body> 태그는 절대 포함하지 않습니다 (layout.jsp가 담당).
  - Tailwind CSS는 layout.jsp에서 이미 로드되어 있으므로 바로 사용 가능합니다.
  - 각 페이지는 "한 개의 메인 <section>"을 중심으로 구성하고, 내부에 grid/flex로 세부 구역을 나눕니다.
  
  🎨 CSS / Tailwind 공통 스타일 규칙
  ----------------------------------------------------------------------------
  ① Layout
     - 기본 컨테이너는 `max-w-3xl mx-auto p-6` (중앙 정렬, 좌우 여백)
     - 섹션 간 구분은 `mt-8` 혹은 `space-y-6` 사용
     - 카드형 박스는 `bg-white rounded-xl shadow p-6` 조합 사용

  ② Text
     - 제목: `text-2xl` ~ `text-4xl font-bold text-brand` 
     - 본문: `text-gray-600` or `text-gray-700`
     - 강조 텍스트: `text-brand-dark font-semibold`

  ③ 버튼 / 인터랙션 요소
     - 기본 버튼: `px-4 py-2 rounded font-semibold transition`
     - 색상 규칙:
       - 기본 버튼: `bg-brand text-white hover:bg-brand-dark`
       - 서브 버튼: `bg-gray-100 text-gray-700 hover:bg-gray-200`
     - 클릭 효과는 `transition` + `hover:` 조합으로 자연스럽게.

  ④ 반응형 (Mobile → Tablet → Desktop)
     - 항상 모바일 퍼스트로 작성
       → 기본 스타일은 모바일 기준으로, 이후 `md:` 이상부터 보강
     - 예시:
         - `text-sm md:text-base lg:text-lg`
         - `grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6`
     - 모바일 메뉴나 숨김요소는 `hidden md:block`, `block md:hidden` 패턴으로 제어

  ⑤ 컬러 네이밍 (Tailwind config에서 정의된 brand 컬러 기준)
     - 메인 컬러: `text-brand`, `bg-brand`
     - Hover 색상: `hover:text-brand-dark`, `hover:bg-brand-dark`
     - 중립 톤: `text-gray-*`, `bg-gray-*`
     - 강조 포인트: `text-accent`, `bg-accent` (선택적)

  ⑥ 접근성 & 폰트
     - 폰트는 header.jsp에서 `font-display` 클래스로 지정되어 있음.
     - 버튼이나 링크는 반드시 `hover:` 외에 `focus:outline-none` 지정 권장.
     - 한글 글꼴 크기는 `text-base` 이상을 기본으로 유지.

  ⑦ 이미지 및 아이콘
     - 이미지는 항상 `rounded-lg shadow-sm w-full h-auto object-cover`
     - 아이콘은 `w-5 h-5 text-brand` 기본 (Tailwind SVG 아이콘 기반)

  ----------------------------------------------------------------------------
    ===================================================================================================== -->
 
 
  <section class="max-w-3xl mx-auto mt-10 bg-white rounded-xl shadow p-6">
    <h1 class="text-3xl font-bold text-brand mb-4">Example Page</h1>
    <p class="text-gray-600 mb-4">이 페이지는 Tiles 레이아웃과 header.jsp가 자동으로 적용된 예제입니다.</p>
    <button id="alertBtn"
            class="px-4 py-2 bg-brand text-white font-semibold rounded hover:bg-brand-dark transition">
      버튼 클릭
    </button>
  </section>

  📱 반응형 예시
  <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-10">
    <div class="bg-white p-4 rounded shadow">Card 1</div>
    <div class="bg-white p-4 rounded shadow">Card 2</div>
    <div class="bg-white p-4 rounded shadow">Card 3</div>
  </div>





<!-- ✅ 페이지별 JS를 직접 작성 가능 -->


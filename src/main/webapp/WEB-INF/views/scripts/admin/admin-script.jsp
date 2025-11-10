<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- ✅ Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
document.addEventListener("DOMContentLoaded", () => {

  // 🟡 챌린지별 참여율 (도넛 차트)
  new Chart(document.getElementById('challengeChart'), {
    type: 'doughnut',
    data: {
      labels: ['나는 오늘도 달린다', '나는 걷기를 사랑해', '나만의 운동 루틴'],
      datasets: [{
        data: [32.7, 10.9, 21.6],
        backgroundColor: ['#ffcc00', '#ff66cc', '#9999ff'],
      }]
    },
    options: {
      plugins: {
        legend: { position: 'bottom' }
      },
      maintainAspectRatio: false
    }
  });

  // 🟠 등급별 회원 평균 거리 (가로 막대)
  new Chart(document.getElementById('memberChart'), {
    type: 'bar',
    data: {
      labels: ['Lv-1', 'Lv-2', 'Lv-3'],
      datasets: [{
        label: '평균 거리 (km)',
        data: [40, 80, 120],
        backgroundColor: ['#9999ff', '#ff9966', '#66cc66']
      }]
    },
    options: {
      indexAxis: 'y',
      maintainAspectRatio: false,
      scales: {
        x: { beginAtZero: true }
      }
    }
  });
});
</script>
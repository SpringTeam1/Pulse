<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- ====================== 📊 Chart.js 연결 ====================== -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  // 주간 활동 그래프
  const ctx1 = document.getElementById('weeklyChart');
  new Chart(ctx1, {
    type: 'bar',
    data: {
      labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      datasets: [{
        label: '러닝 거리 (km)',
        data: [12, 9, 15, 10, 7, 18, 11],
        backgroundColor: '#3B82F6',
        borderRadius: 6
      }]
    },
    options: { responsive: true, plugins: { legend: { display: false } } }
  });

  // 회원 통계 (도넛)
  const ctx2 = document.getElementById('memberChart');
  new Chart(ctx2, {
    type: 'doughnut',
    data: {
      labels: ['브론즈', '실버', '골드', '플래티넘'],
      datasets: [{
        data: [45, 30, 15, 10],
        backgroundColor: ['#BFDBFE', '#60A5FA', '#2563EB', '#1E3A8A']
      }]
    },
    options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
  });
</script>


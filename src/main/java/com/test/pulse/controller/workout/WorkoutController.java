package com.test.pulse.controller.workout;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.pulse.model.user.CustomUser;
import com.test.pulse.model.workout.WorkoutDTO;
import com.test.pulse.model.workout.WorkoutLogDTO;
import com.test.pulse.service.workout.WorkoutService;

import lombok.RequiredArgsConstructor;

/**
 * 운동 관련 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {
	
	private final WorkoutService workoutService;
	
	/**
	 * 내 운동 기록 목록 페이지를 반환한다.
	 *
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @param auth  Authentication 객체
	 * @return 내 운동 기록 목록 페이지 뷰 이름
	 */
	@GetMapping("/log")
	public String workoutLog(Model model, Authentication auth) {
		String accountId = null;
		
		if (auth != null && auth.isAuthenticated()) {
			Object principal = auth.getPrincipal();
			
			if (principal instanceof CustomUser) {
				CustomUser customUser = (CustomUser) principal;
				accountId = customUser.getAdto().getAccountId();
			}
		}
		
		if (accountId !=null) {
			List<WorkoutLogDTO> logs = workoutService.getMyLogs(accountId);
			model.addAttribute("activityLogs", logs);
		}
		
		return "script.workout.workoutlog";
	}
	
	/**
	 * 운동 기록 페이지를 반환한다.
	 *
	 * @return 운동 기록 페이지 뷰 이름
	 */
	@GetMapping("/record")
	public String workoutRecord() {
		return "script.workout.workoutrecord";
	}
	
	/**
	 * 운동 상세 정보 페이지를 반환한다.
	 *
	 * @param workoutSeq 조회할 운동의 번호
	 * @param model      뷰에 전달할 데이터를 담는 Model 객체
	 * @return 운동 상세 정보 페이지 뷰 이름
	 */
	@GetMapping("/detail/{workoutSeq}")
	public String workoutDetail(@PathVariable("workoutSeq") int workoutSeq, Model model) {
		WorkoutDTO workout = workoutService.getWorkoutDetail(workoutSeq);
		model.addAttribute("workout", workout);
		return "script.workout.workoutdetail";
	}
}
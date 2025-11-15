package com.test.pulse.controller.workout;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.pulse.model.user.CustomUser;
import com.test.pulse.model.workout.WorkoutLogDTO;
import com.test.pulse.service.workout.WorkoutService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {
	
	private final WorkoutService workoutService;
	
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
		
		return "workout.workoutlog";
	}
	
	@GetMapping("/record")
	public String workoutRecord() {
		return "script.workout.workoutrecord";
	}
}

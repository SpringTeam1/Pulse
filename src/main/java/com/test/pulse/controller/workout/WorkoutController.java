package com.test.pulse.controller.workout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {
	@GetMapping("/log")
	public String workoutLog() {
		return "workout.workoutlog";
	}
	
	@GetMapping("/record")
	public String workoutRecord() {
		return "script.workout.workoutrecord";
	}
}

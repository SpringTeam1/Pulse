package com.test.pulse.restcontroller.workout;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.model.user.CustomUser;
import com.test.pulse.model.workout.WorkoutDTO;
import com.test.pulse.service.course.CourseService;
import com.test.pulse.service.workout.WorkoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workout")
@RequiredArgsConstructor
public class WorkoutRestController {
	private final WorkoutService workoutService;
	private final Logger log = LoggerFactory.getLogger(WorkoutRestController.class);
	
	//다중 gpx 파일 업로드 API
	@PostMapping("/record/gpx") public ResponseEntity<?> uploadWorkoutLogs(
			@RequestParam("gpxFiles") List<MultipartFile> gpxFiles,
			Authentication auth) {
		//Spring Security: 사용자 아이디 받아오기
		String accountId = null;
		
		if (auth != null && auth.isAuthenticated()) {
			Object principal = auth.getPrincipal();
			
			if (principal instanceof CustomUser) {
				CustomUser customUser = (CustomUser) principal;
				accountId = customUser.getAdto().getAccountId();
			}
		}
		
		// 비로그인 사용자 차단
        if (accountId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
        }
		
		if (gpxFiles.isEmpty()) {
			return ResponseEntity.badRequest().body("파일이 없습니다.");
		}
		
		try {
			List<WorkoutDTO> savedLogs = workoutService.saveWorkoutLogs(gpxFiles, accountId);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedLogs); //json으로 만들어서 반환
		} catch (Exception e) {
			log.error("운동 기록 저장 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
}

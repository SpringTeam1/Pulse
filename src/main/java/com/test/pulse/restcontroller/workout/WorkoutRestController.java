package com.test.pulse.restcontroller.workout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.*;
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
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Workout API")
@RestController
@RequestMapping("/api/workout")
@RequiredArgsConstructor
public class WorkoutRestController {
	private final WorkoutService workoutService;
	private final Logger log = LoggerFactory.getLogger(WorkoutRestController.class);
	
	//다중 gpx 파일 업로드 API
    @ApiOperation(value = "운동 기록(GPX) 업로드", notes = "여러 개의 GPX 파일을 업로드하여 운동 기록을 저장한다. (사진, 코멘트 포함)")
    @ApiResponses({
            @ApiResponse(code = 201, message = "운동 기록 저장 성공"),
            @ApiResponse(code = 400, message = "파일 누락 등 잘못된 요청"),
            @ApiResponse(code = 401, message = "로그인 필요"),
            @ApiResponse(code = 500, message = "서버 오류 (파일 처리 실패)")
    })
	@PostMapping("/record/gpx") public ResponseEntity<?> uploadWorkoutLogs(
            @ApiParam(value = "GPX 파일 리스트 (다중 선택 가능)", required = true)
			@RequestParam("gpxFiles") List<MultipartFile> gpxFiles,

            @ApiParam(value = "사용자 체중 (kg)", required = true, example = "70.5")
			@RequestParam("userWeight") double userWeight,

            @ApiParam(value = "운동 인증 사진 (선택)")
			@RequestParam(value = "attachment", required = false) MultipartFile attachment,

            @ApiParam(value = "운동 한줄평 (선택)", required = false, example = "오늘 날씨가 좋아서 뛰기 편했다.")
            @RequestParam(value = "exerciseComment", required = false) String exerciseComment,
            @ApiIgnore Authentication auth,
            @ApiIgnore HttpServletRequest request) {
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
			List<WorkoutDTO> savedLogs = workoutService.saveWorkoutLogs(gpxFiles, accountId, userWeight, attachment, exerciseComment, request);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedLogs); //json으로 만들어서 반환
		} catch (Exception e) {
			log.error("운동 기록 저장 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
}

package com.test.pulse.restcontroller.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.model.course.GPXCourseDTO;
import com.test.pulse.model.course.ManualCourseDTO;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.course.CourseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 코스 관련 RESTful API를 제공하는 컨트롤러
 */
@Api(tags = "Course API") // [1] 컨트롤러 설명
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseRestController {
	private final CourseService courseService;
	private final Logger log = LoggerFactory.getLogger(CourseService.class);
	
	/**
	 * GPX 파일을 업로드하여 새로운 코스를 생성한다.
	 * @param gpxFile GPX 파일
	 * @param courseName 코스 이름
	 * @param description 코스 설명
	 * @param auth 인증 정보
	 * @return 생성된 코스 정보
	 */
	@ApiOperation(value = "GPX 파일로 코스 등록", notes = "GPX 파일을 업로드하여 새로운 코스를 생성한다.")
	@ApiResponse(code = 500, message = "Internal Server Error")
	@PostMapping(("/gpx"))
	public ResponseEntity<GPXCourseDTO> registerGpxCourse(
		
		@ApiParam(value = "코스 이름", required = true, example = "한강 러닝 코스")
		@RequestParam("courseName") String courseName,
		
		@ApiParam(value = "업로드할 GPX 파일", required = true)
		@RequestParam("gpxFile") MultipartFile gpxFile,
		
		@ApiParam(value = "코스 설명", required = false, example = "초보자도 뛰기 좋은 코스입니다.")
		@RequestParam("description") String description,
		
		@ApiIgnore
		Authentication auth
		//세션 방식으로 변경 위해 필요
		//HttpSession session
	) {
		//String accountId = session.getAttribute(accountId);
		String accountId = null;
		
		if (auth != null && auth.isAuthenticated()) {
			Object principal = auth.getPrincipal();
			
			if (principal instanceof CustomUser) {
				CustomUser customUser = (CustomUser) principal;
				accountId = customUser.getAdto().getAccountId();
			}
		}
		
		try {
			GPXCourseDTO savedCourse = courseService.parseAndSaveGpxCourse(gpxFile, courseName, description, accountId);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * 지도에서 직접 찍은 좌표(JSON)를 이용해 코스를 등록한다.
	 * @param request 수동 코스 등록 요청 정보
	 * @param auth 인증 정보
	 * @return 생성된 코스 정보
	 */
	@ApiOperation(value = "수동 좌표 코스 등록", notes = "지도에서 직접 찍은 좌표(JSON)를 이용해 코스를 등록한다.")
	@ApiResponse(code = 500, message = "Internal Server Error")
	@PostMapping("/manual")
	public ResponseEntity<GPXCourseDTO> registerManualCourse(@RequestBody ManualCourseDTO request, @ApiIgnore Authentication auth){
		log.info("===== RestController (Manual) 진입 =====");
		log.info("받은 좌표 수: {}", request.getCoords().size());
		String accountId = null;
		
		if (auth != null && auth.isAuthenticated()) {
			Object principal = auth.getPrincipal();
			
			if (principal instanceof CustomUser) {
				CustomUser customUser = (CustomUser) principal;
				accountId = customUser.getAdto().getAccountId();
			}
		}
		
		try {
			GPXCourseDTO savedCourse = courseService.saveCourse(
					request.getCoords(), 
					request.getCourseName(), 
					request.getDescription(), 
					accountId);
			log.info("수동 코스 등록 성공. Seq: {}", savedCourse.getCourseSeq());
			return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}

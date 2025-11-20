package com.test.pulse.restcontroller.crew;


import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.model.crew.CrewMemberDTO;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.crew.CrewService;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 크루 관련 RESTful API를 제공하는 컨트롤러
 */
@Api(tags = "Crew API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crew")
public class CrewRestController {

    private final CrewService crewService;

    /**
     * 새로운 크루를 생성한다. 크루 정보와 대표 이미지를 업로드한다.
     * @param crew 크루 정보
     * @param crewAttach 크루 대표 이미지 파일
     * @param req HttpServletRequest 객체
     * @param authentication 인증 정보
     * @return 크루 생성 결과를 담은 ResponseEntity
     */
    @ApiOperation(value = "크루 생성(등록)", notes = "새로운 크루를 생성한다. 크루 정보와 대표 이미지를 업로드한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "크루 생성 성공"),
            @ApiResponse(code = 401, message = "로그인 필요"),
            @ApiResponse(code = 403, message = "이미 크루에 가입된 사용자"),
            @ApiResponse(code = 500, message = "서버 오류 (파일 저장 실패 등)")
    })
    @PostMapping
    public ResponseEntity<?> registerCrew(
            @ApiParam(value = "크루 정보 (이름, 목표, 활동지역 등)", required = true)
            @ModelAttribute CrewDTO crew,

            @ApiParam(value = "크루 대표 이미지 파일")
            @RequestParam(value = "crewAttach", required = false) MultipartFile crewAttach,
            @ApiIgnore HttpServletRequest req,
            @ApiIgnore Authentication authentication) {

        try {

            String accountId = getAccountId(authentication);
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "로그인이 필요한다."));
            }

            // 이미 크루에 가입된 유저는 생성 제한
            if (crewService.isUserInCrew(accountId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("success", false, "message", "이미 크루에 가입되어 있습니다. 기존 크루 탈퇴 후 재시도 해주세요."));
            }

            crew.setAccountId(accountId);

            // 1. 파일 업로드
            if (crewAttach != null && !crewAttach.isEmpty()) {
                String uploadDir = req.getServletContext().getRealPath("/crewmainFile");
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String fileName = System.currentTimeMillis() + "_" + crewAttach.getOriginalFilename();
                File dest = new File(dir, fileName);
                crewAttach.transferTo(dest);

                crew.setCrewAttach(fileName);
            } else {
                crew.setCrewAttach("default.jpg");
            }

            // 2. DB 저장 (MyBatis Mapper 호출)
            int result = crewService.addCrew(crew);

            // 3. 응답 생성
            Map<String, Object> res = new HashMap<>();
            res.put("success", result > 0);
            res.put("message", result > 0 ? "크루가 성공적으로 등록되었습니다!" : "크루 등록에 실패했습니다.");
            res.put("crewName", crew.getCrewName());
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("message", "서버 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
        }
    }

    /**
     * 특정 크루에 소속된 회원들의 목록을 조회한다.
     * @param crewSeq 크루 고유 번호
     * @return 회원 목록을 담은 ResponseEntity
     */
    @ApiOperation(value = "크루 회원 목록 조회", notes = "특정 크루에 소속된 회원들의 목록을 조회한다.")
    @GetMapping("/member/list/{crewSeq}")
    public ResponseEntity<List<CrewMemberDTO>> getMemberList(
            @ApiParam(value = "크루 고유 번호(Seq)", required = true, example = "1")
            @PathVariable("crewSeq") String crewSeq
    ) {
        List<CrewMemberDTO> list = crewService.memberList(crewSeq);
        return ResponseEntity.ok(list);
    }

    /**
     * 공공데이터포털 등 외부 API를 통해 마라톤 대회 정보를 가져온다.
     * @return 마라톤 대회 정보 JSON 문자열
     */
    @ApiOperation(value = "마라톤 정보 조회 (외부 API)", notes = "공공데이터포털 등 외부 API를 통해 마라톤 대회 정보를 가져온다.")
    @GetMapping(value = "/marathonapi", produces = "application/json; charset=UTF-8")
    public String getMarathonApi() {
        return crewService.getMarathon();
    }

    /**
     * 인증 정보에서 사용자 계정 ID를 가져온다.
     * @param authentication 인증 정보
     * @return 사용자 계정 ID
     */
    private String getAccountId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUser)) {
            return null;
        }
        return ((CustomUser) authentication.getPrincipal()).getAdto().getAccountId();
    }

}
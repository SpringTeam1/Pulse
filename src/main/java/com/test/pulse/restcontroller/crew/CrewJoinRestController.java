package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.CrewJoinRequestDTO;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.crew.CrewService;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Api(tags = "Crew Join API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crewjoin")
public class CrewJoinRestController {

    private final CrewService crewService;

    // ✅ 크루 가입 신청
    @ApiOperation(value = "크루 가입 신청", notes = "일반 회원이 특정 크루에 가입을 신청합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "신청 완료"),
            @ApiResponse(code = 400, message = "이미 가입/신청 상태"),
            @ApiResponse(code = 401, message = "로그인 필요")
    })
    @PostMapping("/{crewSeq}")
    public ResponseEntity<?> joinCrew(
            @ApiParam(value = "가입할 크루 번호(Seq)", required = true, example = "1")
            @PathVariable("crewSeq") String crewSeq,
            @ApiIgnore Authentication authentication) {

        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "로그인이 필요합니다."
            ));
        }

        // ✅ 이미 가입 or 신청한 경우 방지
        boolean alreadyJoined = crewService.isUserInCrew(accountId);
        boolean alreadyRequested = crewService.isAlreadyRequested(crewSeq, accountId);

        if (alreadyJoined || alreadyRequested) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "이미 가입 또는 신청한 크루입니다."
            ));
        }

        int result = crewService.requestJoinCrew(crewSeq, accountId);

        return ResponseEntity.ok(Map.of(
                "success", result > 0,
                "message", result > 0 ? "가입 신청 완료!" : "가입 신청 실패!"
        ));
    }

    //  특정 크루의 가입 요청 목록 조회
    @ApiOperation(value = "가입 신청 목록 조회", notes = "크루장이 해당 크루에 들어온 가입 신청 목록을 확인한다.")
    @GetMapping("/list/{crewSeq}")
    public ResponseEntity<?> getJoinRequests(
            @ApiParam(value = "크루 번호(Seq)", required = true)
            @PathVariable String crewSeq) {
        List<CrewJoinRequestDTO> list = crewService.getJoinRequests(crewSeq);
        return ResponseEntity.ok(list);
    }

    // 가입 승인
    @ApiOperation(value = "가입 승인", notes = "크루장이 특정 회원의 가입 요청을 승인한다.")
    @PostMapping("/approve/{crewJoinSeq}")
    public ResponseEntity<?> approveJoin(
            @ApiParam(value = "가입 요청 번호(Seq)", required = true)
            @PathVariable String crewJoinSeq) {
        int result = crewService.approveJoin(crewJoinSeq);
        return ResponseEntity.ok(Map.of(
                "success", result > 0,
                "message", result > 0 ? "가입 승인 완료!" : "승인 실패!"
        ));
    }

    @ApiOperation(value = "크루 멤버 수 갱신", notes = "가입 승인 후 크루의 현재 인원수를 갱신한다. (승인 로직 내부에서 호출될 수 있음")
    @PatchMapping("/approve/memberup/{crewSeq}")
    public ResponseEntity<?> approveMemberup(
            @ApiParam(value = "크루 번호(Seq)", required = true)
            @PathVariable String crewSeq) {
        int result = crewService.upCountCrewMember(crewSeq);

        return ResponseEntity.ok(Map.of(
                "success", result > 0,
                "message", result > 0 ? "가입 승인 완료!" : "승인 실패!"
        ));
    }



    // 가입 거절
    @ApiOperation(value = "가입 거절", notes = "크루장이 특정 회원의 가입 요청을 거절한다.")
    @PostMapping("/reject/{crewJoinSeq}")
    public ResponseEntity<?> rejectJoin(
            @ApiParam(value = "가입 요청 번호(Seq)", required = true)
            @PathVariable String crewJoinSeq) {
        int result = crewService.rejectJoin(crewJoinSeq);
        return ResponseEntity.ok(Map.of(
                "success", result > 0,
                "message", result > 0 ? "가입 거절 처리 완료!" : "거절 실패!"
        ));
    }

    private String getAccountId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUser)) {
            return null;
        }
        return ((CustomUser) authentication.getPrincipal()).getAdto().getAccountId();
    }
}
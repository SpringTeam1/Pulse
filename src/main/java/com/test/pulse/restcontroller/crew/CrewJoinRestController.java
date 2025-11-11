package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.CrewJoinRequestDTO;
import com.test.pulse.service.crew.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crewjoin")
public class CrewJoinRestController {

    private final CrewService crewService;

    // ✅ 크루 가입 신청
    @PostMapping("/{crewSeq}")
    public ResponseEntity<?> joinCrew(@PathVariable("crewSeq") String crewSeq,
                                      HttpSession session) {

        String accountId = (String) session.getAttribute("accountId");

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
    @GetMapping("/list/{crewSeq}")
    public ResponseEntity<?> getJoinRequests(@PathVariable String crewSeq) {
        List<CrewJoinRequestDTO> list = crewService.getJoinRequests(crewSeq);
        return ResponseEntity.ok(list);
    }

    // 가입 승인
    @PostMapping("/approve/{crewJoinSeq}")
    public ResponseEntity<?> approveJoin(@PathVariable String crewJoinSeq) {
        int result = crewService.approveJoin(crewJoinSeq);
        return ResponseEntity.ok(Map.of(
                "success", result > 0,
                "message", result > 0 ? "가입 승인 완료!" : "승인 실패!"
        ));
    }

    @PatchMapping("/approve/memberup/{crewSeq}")
    public ResponseEntity<?> approveMemberup(@PathVariable String crewSeq) {
        int result = crewService.upCountCrewMember(crewSeq);

        return ResponseEntity.ok(Map.of(
                "success", result > 0,
                "message", result > 0 ? "가입 승인 완료!" : "승인 실패!"
        ));
    }



    //  가입 거절
    @PostMapping("/reject/{crewJoinSeq}")
    public ResponseEntity<?> rejectJoin(@PathVariable String crewJoinSeq) {
        int result = crewService.rejectJoin(crewJoinSeq);
        return ResponseEntity.ok(Map.of(
                "success", result > 0,
                "message", result > 0 ? "가입 거절 처리 완료!" : "거절 실패!"
        ));
    }
}
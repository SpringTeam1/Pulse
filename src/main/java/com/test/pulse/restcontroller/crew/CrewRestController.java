package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.service.crew.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crew")
public class CrewRestController {

    private final CrewService crewService;

    @PostMapping
    public ResponseEntity<?> registerCrew(
            @ModelAttribute CrewDTO crew,
            @RequestParam(value = "crewAttach", required = false) MultipartFile crewAttach,
            HttpServletRequest req) {

        try {

            String accountId = (String)req.getSession().getAttribute("accountId");


            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "로그인이 필요합니다."));
            }

            // ✅ 이미 크루에 가입된 유저는 생성 제한
            if (crewService.isUserInCrew(accountId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("success", false, "message", "이미 크루에 가입되어 있습니다. 기존 크루 탈퇴 후 재시도 해주세요."));
            }

            // ✅ 1. 파일 업로드
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

            // ✅ 2. DB 저장 (MyBatis Mapper 호출)
            int result = crewService.addCrew(crew);

            // ✅ 3. 응답 생성
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

}

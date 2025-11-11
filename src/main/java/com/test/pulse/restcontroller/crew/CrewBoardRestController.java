package com.test.pulse.restcontroller.crew;


import com.test.pulse.model.crew.BoardDTO;
import com.test.pulse.service.crew.CrewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/crew/board")
@RequiredArgsConstructor
public class CrewBoardRestController {

    private final CrewBoardService crewBoardService;

    @GetMapping("/{crewSeq}")
    public ResponseEntity<?> list(@PathVariable String crewSeq,
                                  @RequestParam(defaultValue = "1") int page) {
        int pageSize = 10;
        int begin = (page - 1) * pageSize + 1;
        int end = page * pageSize;

        HashMap<String, String> map = new HashMap<>();
        map.put("begin", String.valueOf(begin));
        map.put("end", String.valueOf(end));

        List<BoardDTO> list = crewBoardService.list(map, crewSeq);
        int totalCount = crewBoardService.getTotalCount(crewSeq);

        return ResponseEntity.ok(Map.of(
                "list", list,
                "totalCount", totalCount
        ));
    }

    @PostMapping
    public ResponseEntity<?> add(
            @ModelAttribute BoardDTO boardDTO,
            @RequestParam(value = "attach", required = false) MultipartFile attach,
            HttpServletRequest req
    ) {

        try {
            String uploadPath = req.getServletContext().getRealPath("/crewboardFile");
            Files.createDirectories(Paths.get(uploadPath));

            // ✅ 첨부파일 처리
            if (attach != null && !attach.isEmpty()) {
                String savedFileName = crewBoardService.saveAttach(attach, uploadPath);
                boardDTO.setAttach(savedFileName);
            }



            int result = crewBoardService.add(boardDTO);

            return ResponseEntity.ok(Map.of(
                    "success", result > 0,
                    "message", result > 0 ? "게시글 등록 완료!" : "게시글 등록 실패!"
            ));

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(Map.of("success", false, "message", "서버 오류: " + e.getMessage()));
        }

    }


}

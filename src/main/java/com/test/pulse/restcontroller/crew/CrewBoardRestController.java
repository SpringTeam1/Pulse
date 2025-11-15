package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.BoardDTO;
import com.test.pulse.service.crew.CrewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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

    /**
     * ✅ 게시글 목록 조회
     */
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

    /**
     * 게시글 총 갯수
     */
    @GetMapping("/boardcount/{crewSeq}")
    public ResponseEntity<?> getBoardCount(@PathVariable String crewSeq) {
        int totalCount = crewBoardService.getTotalCount(crewSeq);
        return ResponseEntity.ok(totalCount);
    }

    /**
     * 이번주 게시글 총 갯수
     */
    @GetMapping("/boardtop2count/{crewSeq}")
    public ResponseEntity<?> getBoardTop2Count(@PathVariable String crewSeq) {
        int totalCount = crewBoardService.getTotalCount2Week(crewSeq);
        return ResponseEntity.ok(totalCount);
    }

    /**
     * 이번주 게시글 조회수 top1
     */
    @GetMapping("/boardtop2/{crewSeq}")
    public ResponseEntity<BoardDTO> getBoardTop2(@PathVariable String crewSeq) {
        BoardDTO dto = crewBoardService.getWeeklyTop2Posts(crewSeq);
        return ResponseEntity.ok(dto);
    }

    /**
     * ✅ 게시글 등록
     */
    @PostMapping
    public ResponseEntity<?> add(
            @ModelAttribute BoardDTO boardDTO,
            @RequestParam(value = "attach", required = false) MultipartFile attach,
            HttpServletRequest req
    ) {
        try {
            String uploadPath = req.getServletContext().getRealPath("/crewboardFile");
            Files.createDirectories(Paths.get(uploadPath));

            // 첨부파일 처리
            if (attach != null && !attach.isEmpty()) {
                String savedFileName = crewBoardService.saveAttach(attach, uploadPath);
                boardDTO.setAttach(savedFileName);
            }

            int result = crewBoardService.add(boardDTO);

            if (result > 0) {
                // 201 Created
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                        "success", true,
                        "message", "게시글 등록 완료!"
                ));
            } else {
                // 400 Bad Request
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "게시글 등록 실패!"
                ));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "서버 오류 발생: " + e.getMessage()
            ));
        }
    }

    /**
     * ✅ 좋아요 기능
     */
    @PostMapping("/{boardContentSeq}/like")
    public ResponseEntity<?> likebtn(@PathVariable("boardContentSeq") String boardContentSeq) {
        try {
            int newCount = crewBoardService.updateLike(boardContentSeq);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "favoriteCount", newCount
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "서버 오류 발생: " + e.getMessage()
            ));
        }
    }

    /**
     * 이번주 좋아요 1위 글
     */
    @GetMapping("/boardliketop/{crewSeq}")
    public ResponseEntity<BoardDTO> getLikeTop1BoardContent(
            @PathVariable String crewSeq
    ) {
        BoardDTO dto = crewBoardService.getLikeTop1BoardContent(crewSeq);
        return ResponseEntity.ok(dto);
    }

    /**
     * ✅ 게시글 수정
     */
    @PutMapping("/{boardContentSeq}/edit")
    public ResponseEntity<?> edit(
            @ModelAttribute BoardDTO boardDTO,
            @PathVariable String boardContentSeq,
            @RequestParam(value = "attach", required = false) MultipartFile attachFile,
            HttpServletRequest req
    ) {
        try {
            BoardDTO original = crewBoardService.get(boardContentSeq);

            if (original == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "success", false,
                        "message", "존재하지 않는 게시글입니다."
                ));
            }

            // 파일이 없으면 기존 파일 유지
            if (attachFile == null || attachFile.isEmpty()) {
                boardDTO.setAttach(original.getAttach());
            } else {
                String uploadDir = req.getServletContext().getRealPath("/crewboardFile");
                String fileName = System.currentTimeMillis() + "_" + attachFile.getOriginalFilename();
                attachFile.transferTo(new File(uploadDir, fileName));
                boardDTO.setAttach(fileName);
            }

            int result = crewBoardService.update(boardDTO);
            if (result > 0) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "게시글 수정 완료!"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "게시글 수정에 실패했습니다."
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "서버 오류 발생: " + e.getMessage()
            ));
        }
    }

    /**
     * ✅ 게시글 삭제
     */
    @DeleteMapping("/{boardContentSeq}/del")
    public ResponseEntity<?> delete(
            @PathVariable String boardContentSeq,
            HttpSession session
    ) {
        try {
            String accountId = (String) session.getAttribute("accountId");

            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false,
                        "message", "로그인이 필요합니다."
                ));
            }

            BoardDTO board = crewBoardService.get(boardContentSeq);
            if (board == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "success", false,
                        "message", "존재하지 않는 게시글입니다."
                ));
            }

            if (!board.getAccountId().equals(accountId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "success", false,
                        "message", "본인 글만 삭제할 수 있습니다."
                ));
            }

            int result = crewBoardService.remove(boardContentSeq);
            if (result > 0) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "게시글 삭제 완료!"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "게시글 삭제에 실패했습니다."
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "서버 오류 발생: " + e.getMessage()
            ));
        }
    }
}

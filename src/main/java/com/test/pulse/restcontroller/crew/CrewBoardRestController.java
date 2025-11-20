package com.test.pulse.restcontroller.crew;

import com.test.pulse.model.crew.BoardDTO;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.crew.CrewBoardService;
import com.test.pulse.service.crew.CrewService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 크루 게시판 관련 RESTful API를 제공하는 컨트롤러
 */
@Api(tags = "Crew Board API")
@RestController
@RequestMapping("/api/v1/crew/board")
@RequiredArgsConstructor
public class CrewBoardRestController {

    private final CrewBoardService crewBoardService;
    private final CrewService crewService;

    /**
     * 특정 크루의 게시글 목록을 페이징하여 조회한다.
     * @param crewSeq 크루 번호
     * @param page 페이지 번호
     * @return 게시글 목록과 전체 게시글 수를 담은 ResponseEntity
     */
    @ApiOperation(value = "크루 게시글 목록 조회", notes = "특정 크루의 게시글 목록을 페이징하여 조회한다.")
    @GetMapping("/{crewSeq}")
    public ResponseEntity<?> list(
            @ApiParam(value = "크루 번호(Seq)", required = true, example = "1")
            @PathVariable String crewSeq,
            @ApiParam(value = "페이지 번호", defaultValue = "1",  example = "1")
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
     * 크루의 전체 게시글 수를 반환한다.
     * @param crewSeq 크루 번호
     * @return 전체 게시글 수를 담은 ResponseEntity
     */
    @ApiOperation(value = "게시글 총 개수 조회", notes = "크루의 전체 게시글 수를 반환한다.")
    @GetMapping("/boardcount/{crewSeq}")
    public ResponseEntity<?> getBoardCount(
            @ApiParam(value = "크루 번호(Seq)", required = true)
            @PathVariable String crewSeq) {
        int totalCount = crewBoardService.getTotalCount(crewSeq);
        return ResponseEntity.ok(totalCount);
    }

    /**
     * 이번 주에 작성된 게시글 수를 반환한다.
     * @param crewSeq 크루 번호
     * @return 이번 주 게시글 수를 담은 ResponseEntity
     */
    @ApiOperation(value = "이번 주 게시글 수 조회", notes = "이번 주에 작성된 게시글 수를 반환한다.")
    @GetMapping("/boardtop2count/{crewSeq}")
    public ResponseEntity<?> getBoardTop2Count(
            @ApiParam(value = "크루 번호(Seq)", required = true)
            @PathVariable String crewSeq) {
        int totalCount = crewBoardService.getTotalCount2Week(crewSeq);
        return ResponseEntity.ok(totalCount);
    }

    /**
     * 이번 주 조회수가 가장 높은 게시글을 반환한다.
     * @param crewSeq 크루 번호
     * @return 주간 인기 게시글 정보를 담은 ResponseEntity
     */
    @ApiOperation(value = "주간 인기 게시글(조회수) 조회", notes = "이번 주 조회수가 가장 높은 게시글을 반환한다.")
    @GetMapping("/boardtop2/{crewSeq}")
    public ResponseEntity<BoardDTO> getBoardTop2(
            @ApiParam(value = "크루 번호(Seq)", required = true)
            @PathVariable String crewSeq) {
        BoardDTO dto = crewBoardService.getWeeklyTop2Posts(crewSeq);
        return ResponseEntity.ok(dto);
    }

    /**
     * 크루 게시판에 글을 작성한다. (이미지 첨부 가능)
     * @param boardDTO 게시글 정보
     * @param attach 첨부 파일
     * @param req HttpServletRequest 객체
     * @param authentication 인증 정보
     * @return 게시글 등록 결과를 담은 ResponseEntity
     */
    @ApiOperation(value = "게시글 등록", notes = "크루 게시판에 글을 작성한다. (이미지 첨부 가능)")
    @PostMapping
    public ResponseEntity<?> add(
            @ModelAttribute BoardDTO boardDTO,
            @ApiParam(value = "첨부 파일(이미지)")
            @RequestParam(value = "attach", required = false) MultipartFile attach,
            @ApiIgnore HttpServletRequest req,
            @ApiIgnore Authentication authentication
    ) {
        try {
            String accountId = getAccountId(authentication);
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false,
                        "message", "로그인이 필요한다."
                ));
            }
            String crewSeq = crewService.getCrewSeq(accountId);
            if (crewSeq == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "success", false,
                        "message", "크루에 가입되어 있지 않습니다."
                ));
            }

            boardDTO.setAccountId(accountId);
            try {
                boardDTO.setCrewSeq(Integer.parseInt(crewSeq));
            } catch (NumberFormatException ex) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "유효하지 않은 크루 정보입니다."
                ));
            }
            String nickname = crewService.getAccountIdsNickname(accountId);
            boardDTO.setNickname(nickname);

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
     * 게시글의 좋아요 수를 증가시킨다.
     * @param boardContentSeq 게시글 번호
     * @return 좋아요 처리 결과를 담은 ResponseEntity
     */
    @ApiOperation(value = "게시글 좋아요", notes = "게시글의 좋아요 수를 증가시킨다.")
    @PostMapping("/{boardContentSeq}/like")
    public ResponseEntity<?> likebtn(
            @ApiParam(value = "게시글 번호(Seq)", required = true)
            @PathVariable("boardContentSeq") String boardContentSeq) {
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
     * 이번 주 좋아요 수가 가장 많은 게시글을 반환한다.
     * @param crewSeq 크루 번호
     * @return 주간 인기 게시글(좋아요) 정보를 담은 ResponseEntity
     */
    @ApiOperation(value = "주간 인기 게시글(좋아요) 조회", notes = "이번 주 좋아요 수가 가장 많은 게시글을 반환한다.")
    @GetMapping("/boardliketop/{crewSeq}")
    public ResponseEntity<BoardDTO> getLikeTop1BoardContent(
            @ApiParam(value = "크루 번호(Seq)", required = true)
            @PathVariable String crewSeq
    ) {
        BoardDTO dto = crewBoardService.getLikeTop1BoardContent(crewSeq);
        return ResponseEntity.ok(dto);
    }

    /**
     * 작성한 게시글을 수정한다. (본인 글만 가능)
     * @param boardDTO 수정할 게시글 정보
     * @param boardContentSeq 게시글 번호
     * @param attachFile 수정할 첨부 파일
     * @param req HttpServletRequest 객체
     * @param authentication 인증 정보
     * @return 게시글 수정 결과를 담은 ResponseEntity
     */
    @ApiOperation(value = "게시글 수정", notes = "작성한 게시글을 수정한다. (본인 글만 가능)")
    @PutMapping("/{boardContentSeq}/edit")
    public ResponseEntity<?> edit(
            @ModelAttribute BoardDTO boardDTO,
            @ApiParam(value = "게시글 번호(Seq)", required = true)
            @PathVariable String boardContentSeq,
            @ApiParam(value = "수정할 첨부 파일 (없으면 유지)")
            @RequestParam(value = "attach", required = false) MultipartFile attachFile,
            @ApiIgnore HttpServletRequest req,
            @ApiIgnore Authentication authentication
    ) {
        try {
            String accountId = getAccountId(authentication);
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false,
                        "message", "로그인이 필요한다."
                ));
            }

            BoardDTO original = crewBoardService.get(boardContentSeq);

            if (original == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "success", false,
                        "message", "존재하지 않는 게시글입니다."
                ));
            }

            if (!original.getAccountId().equals(accountId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "success", false,
                        "message", "본인 글만 수정할 수 있습니다."
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

            try {
                boardDTO.setBoardContentSeq(Integer.parseInt(boardContentSeq));
            } catch (NumberFormatException ex) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "유효하지 않은 게시글입니다."
                ));
            }
            boardDTO.setAccountId(accountId);
            boardDTO.setCrewSeq(original.getCrewSeq());

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
     * 게시글을 삭제한다. (본인 글만 가능)
     * @param boardContentSeq 삭제할 게시글 번호
     * @param authentication 인증 정보
     * @return 게시글 삭제 결과를 담은 ResponseEntity
     */
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다. (본인 글만 가능)")
    @DeleteMapping("/{boardContentSeq}/del")
    public ResponseEntity<?> delete(
            @ApiParam(value = "삭제할 게시글 번호(Seq)", required = true)
            @PathVariable String boardContentSeq,
            @ApiIgnore Authentication authentication
    ) {
        try {
            String accountId = getAccountId(authentication);
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false,
                        "message", "로그인이 필요한다."
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
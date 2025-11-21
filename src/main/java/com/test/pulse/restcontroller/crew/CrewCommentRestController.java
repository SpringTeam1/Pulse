package com.test.pulse.restcontroller.crew;


import com.test.pulse.model.crew.CommentDTO;
import com.test.pulse.service.crew.CrewBoardCommentService;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.crew.CrewService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 크루 게시판 댓글 관련 RESTful API를 제공하는 컨트롤러
 */
@Api(tags = "Crew Board Comment API")
@RestController
@RequestMapping("/api/v1/crew/board/comment")
@RequiredArgsConstructor
public class CrewCommentRestController {

    private final CrewBoardCommentService commentService;
    private final CrewService crewService;

    /**
     * 크루 게시판 게시글에 댓글을 등록한다.
     * @param boardContentSeq 게시글 번호
     * @param authentication 인증 정보
     * @param commentDTO 댓글 정보
     * @return 등록된 댓글 정보를 담은 ResponseEntity
     */
    @ApiOperation(value = "댓글 작성", notes = "크루 게시판 게시글에 댓글을 등록한다.")
    @PostMapping("/{boardContentSeq}")
    public ResponseEntity<CommentDTO> add(
            @ApiParam(value = "게시글 번호(Seq)", required = true)
            @PathVariable String boardContentSeq,
            @ApiIgnore Authentication authentication,
            @RequestBody CommentDTO commentDTO
    ) {

        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return ResponseEntity.status(401).build();
        }

        String crewSeq = crewService.getCrewSeq(accountId);
        String nickname = crewService.getAccountIdsNickname(accountId);

        commentDTO.setBoardContentSeq(boardContentSeq);
        commentDTO.setAccountId(accountId);
        commentDTO.setCrewSeq(crewSeq);
        commentDTO.setNickname(nickname);

        commentService.add(commentDTO);

        return ResponseEntity.ok().body(commentDTO);
    }

    /**
     * 특정 게시글에 달린 모든 댓글을 조회한다.
     * @param boardContentSeq 게시글 번호
     * @param authentication 인증 정보
     * @return 댓글 목록을 담은 ResponseEntity
     */
    @ApiOperation(value = "댓글 목록 조회", notes = "특정 게시글에 달린 모든 댓글을 조회한다.")
    @GetMapping("/{boardContentSeq}")
    public ResponseEntity<List<CommentDTO>> getCommentBoardContentSeq (
            @ApiParam(value = "게시글 번호(Seq)", required = true)
            @PathVariable String boardContentSeq,
            @ApiIgnore Authentication authentication) {
        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return ResponseEntity.status(401).build();
        }

        List<CommentDTO> list = commentService.getCommentBoardContentSeq(boardContentSeq);

        return ResponseEntity.ok(list);
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
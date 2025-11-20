package com.test.pulse.restcontroller.crew;


import com.test.pulse.model.crew.CommentDTO;
import com.test.pulse.service.crew.CrewBoardCommentService;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.crew.CrewService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Crew Board Comment API")
@RestController
@RequestMapping("/api/v1/crew/board/comment")
@RequiredArgsConstructor
public class CrewCommentRestController {

    private final CrewBoardCommentService commentService;
    private final CrewService crewService;

    @PostMapping("/{boardContentSeq}")
    public ResponseEntity<CommentDTO> add(
            @PathVariable String boardContentSeq,
            Authentication authentication,
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


    @GetMapping("/{boardContentSeq}")
    public ResponseEntity<List<CommentDTO>> getCommentBoardContentSeq
            (
                    @PathVariable String boardContentSeq,
                    Authentication authentication
            ) {

        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return ResponseEntity.status(401).build();
        }

        List<CommentDTO> list = commentService.getCommentBoardContentSeq(boardContentSeq);


        return ResponseEntity.ok(list);
    }

    private String getAccountId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUser)) {
            return null;
        }
        return ((CustomUser) authentication.getPrincipal()).getAdto().getAccountId();
    }
}
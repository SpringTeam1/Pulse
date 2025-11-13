package com.test.pulse.restcontroller.crew;


import com.test.pulse.model.crew.CommentDTO;
import com.test.pulse.service.crew.CrewBoardCommentService;
import com.test.pulse.service.crew.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crew/board/comment")
@RequiredArgsConstructor
public class CrewCommentRestController {

    private final CrewBoardCommentService commentService;
    private final CrewService crewService;

    @PostMapping("/{boardContentSeq}")
    public ResponseEntity<CommentDTO> add(
            @PathVariable String boardContentSeq,
            HttpSession session,
            @RequestBody CommentDTO commentDTO
    ) {

        String accountId = (String) session.getAttribute("accountId");
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
                    HttpSession session
            ) {

        String accountId = (String) session.getAttribute("accountId");
        String nickname = crewService.getAccountIdsNickname(accountId);
        List<CommentDTO> list = commentService.getCommentBoardContentSeq(boardContentSeq);


        return ResponseEntity.ok(list);
    }

}

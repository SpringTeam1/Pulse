package com.test.pulse.service.crew;

import com.test.pulse.mapper.CrewBoardCommentMapper;
import com.test.pulse.model.crew.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 크루 게시판 댓글 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class CrewBoardCommentService {

    private final CrewBoardCommentMapper mapper;

    /**
     * 댓글을 추가한다.
     * @param dto 추가할 댓글 정보
     * @return 추가된 댓글의 수
     */
    public int add(CommentDTO dto) {
        return mapper.add(dto);
    }

    /**
     * 특정 게시글에 달린 모든 댓글을 조회한다.
     * @param boardContentSeq 게시글 번호
     * @return 댓글 목록
     */
    public List<CommentDTO> getCommentBoardContentSeq(String boardContentSeq) {
        return mapper.list(boardContentSeq);
    }


}

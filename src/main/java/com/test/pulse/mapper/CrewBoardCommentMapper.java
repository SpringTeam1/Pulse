package com.test.pulse.mapper;

import com.test.pulse.model.crew.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 크루 게시판 댓글(`tblCrewBoardComment`)에 대한 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
@Mapper
public interface CrewBoardCommentMapper {

    /**
     * 새로운 댓글을 크루 게시글에 추가한다.
     *
     * @param dto 등록할 댓글 정보를 담은 DTO
     * @return 영향을 받은 행의 수
     */
    int add(CommentDTO dto);

    /**
     * 특정 크루 게시글에 달린 모든 댓글 목록을 조회한다.
     *
     * @param boardContentSeq 댓글 목록을 조회할 게시글의 고유 번호(PK)
     * @return 해당 게시글의 댓글 DTO 리스트
     */
    List<CommentDTO> list(String boardContentSeq);
}

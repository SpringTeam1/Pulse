package com.test.pulse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.test.pulse.model.boardnotice.BoardNoticeDTO;

@Mapper   // 🔥 MyBatis가 이 인터페이스를 Mapper로 인식하게 해주는 어노테이션
public interface BoardNoticeMapper {

    /**
     * 🔵 공지 목록 조회 (boardContentTypeSeq = 2인 글만)
     */
    List<BoardNoticeDTO> list();

    /**
     * 🔵 상세보기 (게시글 1개 조회)
     * @param seq 게시글 번호(PK)
     */
    BoardNoticeDTO view(int seq);

    /**
     * 🔵 게시글 등록(INSERT)
     */
    void add(BoardNoticeDTO dto);

    /**
     * 🔵 게시글 수정(UPDATE)
     */
    void edit(BoardNoticeDTO dto);

    /**
     * 🔵 게시글 삭제(DELETE)
     */
    void del(int seq);
    
    /**
     * 🔵 조회수 증가
     */
    int increaseReadCount(int seq);
    
    /**
     * 🔵 페이징 목록
     */
    List<BoardNoticeDTO> listPaging(@Param("offset") int offset,
                                    @Param("pageSize") int pageSize);
	
    /**
     * 🔵 총 게시물 수 (공지글만 카운트)
     */
    int totalCount();
}

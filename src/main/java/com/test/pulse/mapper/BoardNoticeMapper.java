package com.test.pulse.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.test.pulse.model.boardnotice.BoardNoticeDTO;

@Mapper   // 🔥 MyBatis가 이 인터페이스를 Mapper로 인식하게 해주는 어노테이션
public interface BoardNoticeMapper {

    /**
     * 🔵 공지 목록 조회
     * mapper.xml의 <select id="list"> 와 반드시 동일한 이름이어야 함
     */
    List<BoardNoticeDTO> list();

    /**
     * 🔵 상세보기 (게시글 1개 조회)
     * mapper.xml의 <select id="view"> 와 매칭됨
     * @param seq 게시글 번호(PK)
     */
    BoardNoticeDTO view(int seq);

    /**
     * 🔵 게시글 등록(INSERT)
     * mapper.xml의 <insert id="add"> 와 매칭됨
     */
    void add(BoardNoticeDTO dto);

    /**
     * 🔵 게시글 수정(UPDATE)
     * mapper.xml의 <update id="edit"> 와 매칭됨
     */
    void edit(BoardNoticeDTO dto);

    /**
     * 🔵 게시글 삭제(DELETE)
     * mapper.xml의 <delete id="del"> 와 매칭됨
     */
    void del(int seq);
    
    //조회수 증가
    int increaseReadCount(int seq);
    
    //페이징 목록 가져오기
	List<BoardNoticeDTO> listPaging(@Param("offset") int offset,@Param("pageSize") int pageSize);
	
	//총 게시물 수
	int totalCount();
    
}

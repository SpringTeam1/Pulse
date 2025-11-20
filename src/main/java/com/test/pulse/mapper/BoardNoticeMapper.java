package com.test.pulse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.test.pulse.model.boardnotice.BoardNoticeDTO;

/**
 * 공지사항 게시판(`tblBoard`)에 대한 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
@Mapper
public interface BoardNoticeMapper {

    /**
     * 모든 공지사항 목록을 조회한다.
     *
     * @return 공지사항 DTO 리스트
     */
    List<BoardNoticeDTO> list();

    /**
     * 지정된 번호(PK)에 해당하는 공지사항의 상세 정보를 조회한다.
     *
     * @param seq 조회할 게시글의 고유 번호(PK)
     * @return 조회된 공지사항 DTO. 해당 번호의 게시글이 없을 경우 null을 반환할 수 있다.
     */
    BoardNoticeDTO view(int seq);

    /**
     * 새로운 공지사항을 데이터베이스에 등록한다.
     *
     * @param dto 등록할 공지사항 정보를 담은 DTO
     */
    void add(BoardNoticeDTO dto);

    /**
     * 기존 공지사항의 내용을 수정한다.
     *
     * @param dto 수정할 공지사항 정보를 담은 DTO
     */
    void edit(BoardNoticeDTO dto);

    /**
     * 지정된 번호(PK)에 해당하는 공지사항을 삭제한다.
     *
     * @param seq 삭제할 게시글의 고유 번호(PK)
     */
    void del(int seq);
    
    /**
     * 지정된 공지사항의 조회수를 1 증가시킵니다.
     *
     * @param seq 조회수를 증가시킬 게시글의 고유 번호(PK)
     * @return 영향을 받은 행의 수
     */
    int increaseReadCount(int seq);
    
    /**
     * 페이징 처리를 적용하여 공지사항 목록의 특정 페이지를 조회한다.
     *
     * @param offset   조회를 시작할 위치 (0부터 시작)
     * @param pageSize 한 페이지에 표시할 게시글의 수
     * @return 페이징 처리된 공지사항 DTO 리스트
     */
    List<BoardNoticeDTO> listPaging(@Param("offset") int offset,
                                    @Param("pageSize") int pageSize);
	
    /**
     * 전체 공지사항 게시물의 총 개수를 조회한다.
     *
     * @return 총 게시물 수
     */
    int totalCount();
}

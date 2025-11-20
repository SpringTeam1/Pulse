package com.test.pulse.mapper;


import com.test.pulse.model.crew.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.HashMap;
import java.util.List;

/**
 * 크루 전용 게시판(`tblCrewBoard`)에 대한 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
@Mapper
public interface CrewBoardMapper {

    /**
     * 특정 크루의 게시글 목록을 페이징 및 검색 조건을 적용하여 조회한다.
     *
     * @param map     페이징(offset, size) 및 검색(column, word) 정보를 담은 HashMap
     * @param crewSeq 조회할 크루의 고유 번호(PK)
     * @return 조회된 게시글 DTO 리스트
     */
    List<BoardDTO> list(@Param("map") HashMap<String, String> map,
                        @Param("crewSeq") String crewSeq);

    /**
     * 지정된 번호(PK)에 해당하는 게시글의 상세 정보를 조회한다.
     *
     * @param boardContentSeq 조회할 게시글의 고유 번호(PK)
     * @return 조회된 게시글 DTO
     */
    BoardDTO get(@Param("boardContentSeq") String boardContentSeq);

    /**
     * 새로운 게시글을 크루 게시판에 등록한다.
     *
     * @param dto 등록할 게시글 정보를 담은 DTO
     * @return 영향을 받은 행의 수
     */
    int add(BoardDTO dto);

    /**
     * 기존 게시글의 내용을 수정한다.
     *
     * @param dto 수정할 게시글 정보를 담은 DTO
     * @return 영향을 받은 행의 수
     */
    int update(BoardDTO dto);

    /**
     * 지정된 번호(PK)에 해당하는 게시글을 삭제한다.
     *
     * @param boardContentSeq 삭제할 게시글의 고유 번호(PK)
     * @return 영향을 받은 행의 수
     */
    int remove(@Param("boardContentSeq") String boardContentSeq);

    /**
     * 특정 크루의 전체 게시글 수를 조회한다.
     *
     * @param crewSeq 게시글 수를 조회할 크루의 고유 번호(PK)
     * @return 해당 크루의 총 게시글 수
     */
    int getTotalCount(@Param("crewSeq") String crewSeq);

    /**
     * 지정된 게시글의 조회수를 1 증가시킨다.
     *
     * @param boardContentSeq 조회수를 증가시킬 게시글의 고유 번호(PK)
     */
    void updateReadCount(@Param("boardContentSeq") String boardContentSeq);

    /**
     * 지정된 게시글의 '좋아요' 수를 1 증가시킨다.
     *
     * @param boardContentSeq '좋아요' 수를 증가시킬 게시글의 고유 번호(PK)
     */
    void updateLike(@Param("boardContentSeq") String boardContentSeq);

    /**
     * 특정 크루의 모든 게시글에 포함된 사진 목록을 조회한다.
     *
     * @param crewSeq 사진을 조회할 크루의 고유 번호(PK)
     * @return 해당 크루의 사진을 포함한 게시글 DTO 리스트
     */
    List<BoardDTO> getBoardPhotosByCrewSeq(@Param("crewSeq") String crewSeq);

    /**
     * 지정된 게시글의 '좋아요' 수를 조회한다.
     *
     * @param boardContentSeq '좋아요' 수를 조회할 게시글의 고유 번호(PK)
     * @return 해당 게시글의 '좋아요' 수
     */
    int getFavoriteCount (@Param("boardContentSeq") String boardContentSeq);

    /**
     * 특정 크루의 최근 2주간 작성된 게시글 수를 조회한다.
     *
     * @param crewSeq 게시글 수를 조회할 크루의 고유 번호(PK)
     * @return 최근 2주간의 게시글 정보를 담은 DTO (주로 개수 정보를 활용)
     */
    BoardDTO getTotalCount2Week(@Param("crewSeq") String crewSeq);

    /**
     * 특정 크루의 최근 1주간 작성된 게시글 수를 조회한다.
     *
     * @param crewSeq 게시글 수를 조회할 크루의 고유 번호(PK)
     * @return 최근 1주간의 총 게시글 수
     */
    int getTotalPostCountWeek(String crewSeq);

    /**
     * 특정 크루에서 '좋아요'를 가장 많이 받은 게시글을 조회한다.
     *
     * @param crewSeq 크루의 고유 번호(PK)
     * @return '좋아요'를 가장 많이 받은 게시글 DTO
     */
    BoardDTO getLikeTop1BoardContent(@Param("crewSeq") String crewSeq);
}


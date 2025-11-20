package com.test.pulse.mapper;

import java.util.List;
import java.util.Map;

import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

/**
 * 건의사항 게시판에 대한 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
public interface BoardSuggestionMapper {

	/**
     * 모든 건의사항 목록을 조회한다.
     *
     * @return 건의사항 DTO 리스트
     */
	List<BoardSuggestionDTO> suggestionList();
	
	/**
     * 지정된 번호(PK)에 해당하는 건의사항의 상세 정보를 조회한다.
     *
     * @param boardContentSeq 조회할 건의사항의 고유 번호(PK)
     * @return 조회된 건의사항 DTO
     */
	BoardSuggestionDTO getSuggestion(String boardContentSeq);
	
	/**
     * 새로운 건의사항을 데이터베이스에 등록한다.
     *
     * @param sdto 등록할 건의사항 정보를 담은 DTO
     */
	void addSuggestion(BoardSuggestionDTO sdto);
	
	/**
     * 기존 건의사항의 내용을 수정한다.
     *
     * @param sdto 수정할 건의사항 정보를 담은 DTO
     * @return 영향을 받은 행의 수
     */
	int editSuggestion(BoardSuggestionDTO sdto);
	
	/**
     * 지정된 번호(PK)에 해당하는 건의사항을 삭제한다.
     *
     * @param boardContentSeq 삭제할 건의사항의 고유 번호(PK)
     */
	void delSuggestion(String boardContentSeq);

	/**
     * 검색어와 정렬 조건을 포함한 파라미터로 건의사항 목록을 조회한다.
     *
     * @param param 검색어('keyword')와 정렬 조건('sort')을 담은 Map
     * @return 검색 및 정렬된 건의사항 DTO 리스트
     */
	List<BoardSuggestionDTO> searchAndSort(Map<String, Object> param);

	/**
     * 지정된 검색어에 해당하는 전체 게시물 수를 조회한다.
     *
     * @param keyword 검색어
     * @return 조건에 맞는 총 게시물 수
     */
	int getTotalCount(String keyword);

	/**
     * 지정된 건의사항의 '좋아요' 수를 1 증가시킨다.
     *
     * @param boardContentSeq '좋아요' 수를 증가시킬 건의사항의 고유 번호(PK)
     */
	void updateLike(String boardContentSeq);

	/**
     * 지정된 건의사항의 '좋아요' 수를 조회한다.
     *
     * @param boardContentSeq '좋아요' 수를 조회할 건의사항의 고유 번호(PK)
     * @return 해당 건의사항의 '좋아요' 수
     */
	int getFavoriteCount(String boardContentSeq);

	
}

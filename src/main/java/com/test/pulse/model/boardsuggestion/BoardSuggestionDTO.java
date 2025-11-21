package com.test.pulse.model.boardsuggestion;

import com.test.pulse.model.user.AccountInfoDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 건의사항 게시판의 단일 게시글 정보를 담는 데이터 전송 객체(DTO)
 */
@Getter
@Setter
@ToString
public class BoardSuggestionDTO {

	/**
     * 게시글의 고유 번호 (Primary Key)
     */
	private String boardContentSeq;
	
	/**
     * 게시글을 작성한 사용자의 계정 ID
     */
	private String accountId;
	
	/**
     * 게시글의 제목
     */
	private String title;
	
	/**
     * 게시글의 내용
     */
	private String content;
	
	/**
     * 첨부 파일의 파일명 (또는 경로)
     */
	private String attach;
	
	/**
     * 게시글 작성일
     */
	private String regdate;
	
	/**
     * 게시글 조회수
     */
	private int readCount;
	
	/**
     * 게시글 '좋아요' 수
     */
	private int favoriteCount;
	
	/**
     * 게시글의 종류를 구분하는 번호
     */
	private String boardContentTypeSeq;
	
	/**
     * 게시글 작성자의 계정 정보 DTO
     */
	private AccountInfoDTO adto;
	
}

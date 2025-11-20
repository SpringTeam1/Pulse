package com.test.pulse.model.boardnotice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 공지사항 게시판의 단일 게시글 정보를 담는 데이터 전송 객체(DTO)
 */
@Getter
@Setter
@ToString
public class BoardNoticeDTO {
    
    /**
     * 게시글의 고유 번호 (Primary Key)
     */
    private int boardSeq;
    
    /**
     * 게시글을 작성한 사용자의 계정 ID (FK: TBLACCOUNTINFO.ACCOUNTID)
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
     * 게시글 작성일
     */
    private String regdate;
    
    /**
     * 게시글 조회수
     */
    private int readCount;
    
    /**
     * 첨부 파일의 파일명 (또는 경로)
     */
    private String attach;
    
    /**
     * 게시글의 종류를 구분하는 번호 (1: 일반글, 2: 공지글)
     */
    private int boardContentTypeSeq;
    
    /**
     * 게시글을 작성한 사용자의 닉네임 (JOIN을 통해 조회)
     */
    private String nickname;
}

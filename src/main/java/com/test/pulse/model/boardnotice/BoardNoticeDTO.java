package com.test.pulse.model.boardnotice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardNoticeDTO {
    
    private int boardSeq;              // PK
    private String accountId;          // 🔹 작성자 ID (FK → TBLACCOUNTINFO.ACCOUNTID)
    private String title;              // 제목
    private String content;            // 내용
    private String regdate;            // 작성일 (String으로 받고 있음)
    private int readCount;             // 조회수
    private String attach;             // 첨부파일 이름(경로)
    private int boardContentTypeSeq;   // 🔹 1: 일반글, 2: 공지글
    
    private String nickname;		   // join으로 가져온 작성자 이름
}

package com.test.pulse.model.boardnotice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardNoticeDTO {
	
	private int boardSeq;
    private String writer;
    private String title;
    private String content;
    private String regdate;
    private int readCount;
    private String attach;

}

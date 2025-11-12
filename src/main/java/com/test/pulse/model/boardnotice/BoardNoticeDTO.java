package com.test.pulse.model.boardnotice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardNoticeDTO {
	
	private String boardContentSeq;
	private String accountId;
	private String title;
	private String content;
	private String attach;
	private String regdate;
	private String readCount;
	private String favoriteCount;
	private String boardContentTypeSeq;

}

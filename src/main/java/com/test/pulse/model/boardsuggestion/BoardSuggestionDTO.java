package com.test.pulse.model.boardsuggestion;

import com.test.pulse.model.user.AccountInfoDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardSuggestionDTO {

	private String boardContentSeq;
	private String accountId;
	private String title;
	private String content;
	private String attach;
	private String regdate;
	private int readCount;
	private int favoriteCount;
	private String boardContentTypeSeq;
	
	private AccountInfoDTO adto;
	
}

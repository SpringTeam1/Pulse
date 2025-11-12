package com.test.pulse.mapper;

import java.util.List;

import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

public interface BoardSuggestionMapper {

	List<BoardSuggestionDTO> suggestionList();
	
	BoardSuggestionDTO getSuggestion(String boardContentSeq);
	
	void addSuggestion(BoardSuggestionDTO sdto);
	
	void editSuggestion(BoardSuggestionDTO sdto);

	void delSuggestion(String boardContentSeq);
}

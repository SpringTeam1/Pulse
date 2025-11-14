package com.test.pulse.mapper;

import java.util.List;
import java.util.Map;

import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

public interface BoardSuggestionMapper {

	List<BoardSuggestionDTO> suggestionList();
	
	BoardSuggestionDTO getSuggestion(String boardContentSeq);
	
	void addSuggestion(BoardSuggestionDTO sdto);
	
	int editSuggestion(BoardSuggestionDTO sdto);
	
	void delSuggestion(String boardContentSeq);

	List<BoardSuggestionDTO> searchAndSort(Map<String, Object> param);

	int getTotalCount(String keyword);

	void updateLike(String boardContentSeq);

	int getFavoriteCount(String boardContentSeq);

	
}

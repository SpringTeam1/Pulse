package com.test.pulse.controller.boardsuggestion;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.pulse.mapper.BoardSuggestionMapper;
import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardSuggestionController {
	
	private final BoardSuggestionMapper mapper;
	
	@GetMapping("/boardsuggestion/list")
	public String list(Model model) {
		
		List<BoardSuggestionDTO> slist = mapper.suggestionList();
		model.addAttribute("list", slist);
		
		return "boardsuggestion.list";
	}
	
	@GetMapping("/boardsuggestion/view")
	public String view(Model model, String boardContentSeq) {

		BoardSuggestionDTO sdto = mapper.get(boardContentSeq);
		model.addAttribute("dto", sdto);
		
		return "boardsuggestion.view";
	}

}

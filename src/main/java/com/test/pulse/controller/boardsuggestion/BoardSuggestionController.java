package com.test.pulse.controller.boardsuggestion;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.pulse.mapper.BoardSuggestionMapper;
import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardSuggestionController {
	
	private final BoardSuggestionMapper mapper;
	
	@GetMapping("/boardsuggestion")
	public String list(Model model) {
		
		List<BoardSuggestionDTO> slist = mapper.suggestionList();
		
		model.addAttribute("list", slist);
				
		return "list";
	}

}

package com.test.pulse.controller.boardsuggestion;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

		BoardSuggestionDTO sdto = mapper.getSuggestion(boardContentSeq);
		model.addAttribute("dto", sdto);
		
		return "boardsuggestion.view";
	}
	
	@GetMapping("/boardsuggestion/add")
	public String add(Model model) {
		
		return "boardsuggestion.add";
	}
	
	@PostMapping("boardsuggestion/addok")
	public String addok(Model model, BoardSuggestionDTO sdto) {
		
//		CustomUser cuser = (CustomUser)auth.getPrincipal();
//		AccountInfoDTO adto = member.getDto(); 
//		sdto.setAccountId(adto.getAccountId());

		mapper.addSuggestion(sdto);
		
		return "redirect:/boardsuggestion/list";
	}
	
	@GetMapping("/boardsuggestion/edit")
	public String edit(Model model, String boardContentSeq) {
		
		BoardSuggestionDTO sdto = mapper.getSuggestion(boardContentSeq);
		model.addAttribute("dto", sdto);
		
		return "board.edit";
	}
	
	@PostMapping("/boardsuggestion/editok")
	public String editok(Model model, BoardSuggestionDTO sdto) {
		
		mapper.editSuggestion(sdto);
		
		return "redirect:/boardsuggestion/view?boardContentSeq=" + sdto.getBoardContentSeq();
	}
	
	@GetMapping("/boardsuggestion/del")
	public String del(Model model, String boardContentSeq) {
		
		mapper.delSuggestion(boardContentSeq);
		
		return "redirect:/boardSuggestion/list";
	}
	
	

}




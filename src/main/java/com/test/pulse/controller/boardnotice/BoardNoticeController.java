package com.test.pulse.controller.boardnotice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.pulse.mapper.BoardNoticeMapper;
import com.test.pulse.model.boardnotice.BoardNoticeDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boardnotice")
public class BoardNoticeController {
	
	private final BoardNoticeMapper mapper;
	
	@GetMapping("/list.do")
	public String list(Model model) {
		
		model.addAttribute("list", mapper.list());
		
		return "boardnotice.list";
	}
	
	@GetMapping("/view.do")
	public String view(Model model, int seq) {
		
		model.addAttribute("dto", mapper.view(seq));
		
		return "boardnotice.view";
	}
	
	@GetMapping("/add.do")
	public String add() {
		
		return "boardnotice.add";
	}
	
	@PostMapping("/addok.do")
	public String addok(BoardNoticeDTO dto) {
		
		mapper.add(dto);
		
		return "redirect:/boardnotice/list.do";
	}
	
	@GetMapping("/edit.do")
	public String edit(Model model, int seq) {
		
		model.addAttribute("dto", mapper.view(seq));
		
		return "boardnotice.edit";
	}
	
	@PostMapping("/editok.do")
	public String editok(BoardNoticeDTO dto) {
		
		mapper.edit(dto);
		
		return "redirect:/boardnotice/list.do";
	}
	
	@GetMapping("/del.do")
	public String del(int seq) {
		
		mapper.del(seq);
		
		return "redirect:/boardnotice/list.do";
	}
	
}

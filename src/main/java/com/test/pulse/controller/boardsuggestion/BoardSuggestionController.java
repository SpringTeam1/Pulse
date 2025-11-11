package com.test.pulse.controller.boardsuggestion;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.mapper.BoardSuggestionMapper;
import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;
import com.test.pulse.model.user.AccountInfoDTO;

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
		sdto.setAccountId(((BoardSuggestionDTO) mapper).getAccountId());

		mapper.addSuggestion(sdto);
		
		return "redirect:/boardsuggestion/list";
	}
	
	@GetMapping("/boardsuggestion/edit")
	public String edit(Model model, String boardContentSeq) {
		
		BoardSuggestionDTO sdto = mapper.getSuggestion(boardContentSeq);
		model.addAttribute("dto", sdto);
		
		return "boardsuggestion.edit";
	}
	
	
	
	@PostMapping("/boardsuggestion/editok")
	public String editok(
			@ModelAttribute BoardSuggestionDTO sdto,
	                     @RequestParam(value = "attach", required = false) MultipartFile attach,
	                     HttpServletRequest req) {

	    try {
	        // 파일 업로드가 있을 경우
	        if (attach != null && !attach.isEmpty()) {

	            // webapp/boardsuggestion 경로 지정
	            String path = req.getServletContext().getRealPath("/boardsuggestion");
	            File dir = new File(path);
	            if (!dir.exists()) dir.mkdirs();

	            // 실제 업로드 파일명
	            String filename = attach.getOriginalFilename();

	            // 저장 경로
	            File saveFile = new File(dir, filename);
	            attach.transferTo(saveFile);

	            // DTO에 파일명 저장
	            sdto.setAttach(filename);
	        }

	        // DB 수정 처리
	        mapper.editSuggestion(sdto);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/boardsuggestion/edit?boardContentSeq=" + sdto.getBoardContentSeq() + "&error=upload";
	    }

	    // 정상 완료 시 상세보기로 리다이렉트
	    return "redirect:/boardsuggestion/view?boardContentSeq=" + sdto.getBoardContentSeq();
	}
	
	
	
	
	@GetMapping("/boardsuggestion/del")
	public String del(Model model, String boardContentSeq) {
		
		mapper.delSuggestion(boardContentSeq);
		
		return "redirect:/boardSuggestion/list";
	}
	
	

}




package com.test.pulse.controller.boardsuggestion;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.mapper.BoardSuggestionMapper;
import com.test.pulse.model.boardsuggestion.BoardSuggestionDTO;
import com.test.pulse.model.user.AccountInfoDTO;
import com.test.pulse.model.user.CustomUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boardsuggestion")
public class BoardSuggestionController {
	
	private final BoardSuggestionMapper mapper;
	
	/** 
	 * 게시판 목록보기
	 * @return
	 */
	@GetMapping("/list")
	public String list() {
		 // REST에서는 별도 데이터 전달 불필요
		return "script.boardsuggestion.list";
	}
	
	/** 상세 화면 (SSR 렌더링) */
	@GetMapping("/view")
	public String view(Model model, String boardContentSeq) {

		BoardSuggestionDTO sdto = mapper.getSuggestion(boardContentSeq);
		
		// 파일 확장자 추출 (이미지 판별용)
        String fileExt = null;
        if (sdto.getAttach() != null) {
            int idx = sdto.getAttach().lastIndexOf(".");
            if (idx != -1) {
                fileExt = sdto.getAttach().substring(idx + 1).toLowerCase();
            }
        }
		
		model.addAttribute("dto", sdto);
		model.addAttribute("fileExt", fileExt);
		
		return "script.boardsuggestion.view";
	}
	
	
	@GetMapping("/edit")
	public String edit(Model model, String boardContentSeq) {
		
		BoardSuggestionDTO sdto = mapper.getSuggestion(boardContentSeq);
		model.addAttribute("dto", sdto);
		
		return "script.boardsuggestion.edit";
	}
	
	
	@GetMapping("/add")
	public String add(Model model) {
		
		return "script.boardsuggestion.add";
	}
	
	@PostMapping("/addok")
	public String addok(Model model, BoardSuggestionDTO sdto, Authentication auth) {
		
		CustomUser cuser = (CustomUser)auth.getPrincipal();
		AccountInfoDTO adto = cuser.getAdto();
		
		sdto.setAccountId(adto.getAccountId());

		mapper.addSuggestion(sdto);
		
		return "redirect:/boardsuggestion/list";
	}

}




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

/**
 * 건의사항 게시판 관련 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/boardsuggestion")
public class BoardSuggestionController {
	
	private final BoardSuggestionMapper mapper;
	
	/** 
	 * 건의사항 게시판 목록 페이지를 반환한다.
	 * @return 건의사항 목록 페이지 뷰 이름
	 */
	@GetMapping("/list")
	public String list() {
		 // REST에서는 별도 데이터 전달 불필요
		return "script.boardsuggestion.list";
	}
	
	/**
	 * 건의사항 상세 페이지를 반환한다.
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @param boardContentSeq 조회할 건의사항의 번호
	 * @return 건의사항 상세 페이지 뷰 이름
	 */
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
	
	/**
	 * 건의사항 수정 페이지를 반환한다.
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @param boardContentSeq 수정할 건의사항의 번호
	 * @return 건의사항 수정 페이지 뷰 이름
	 */
	@GetMapping("/edit")
	public String edit(Model model, String boardContentSeq) {
		
		BoardSuggestionDTO sdto = mapper.getSuggestion(boardContentSeq);
		model.addAttribute("dto", sdto);
		
		return "script.boardsuggestion.edit";
	}
	
	/**
	 * 건의사항 작성 페이지를 반환한다.
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @return 건의사항 작성 페이지 뷰 이름
	 */
	@GetMapping("/add")
	public String add(Model model) {
		
		return "script.boardsuggestion.add";
	}
	
	/**
	 * 작성된 건의사항을 등록한다.
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @param sdto 작성된 건의사항 정보를 담은 DTO 객체
	 * @param auth Authentication 객체
	 * @return 건의사항 목록으로 리다이렉트
	 */
	@PostMapping("/addok")
	public String addok(Model model, BoardSuggestionDTO sdto, Authentication auth) {
		
		CustomUser cuser = (CustomUser)auth.getPrincipal();
		AccountInfoDTO adto = cuser.getAdto();
		
		sdto.setAccountId(adto.getAccountId());

		mapper.addSuggestion(sdto);
		
		return "redirect:/boardsuggestion/list";
	}

}
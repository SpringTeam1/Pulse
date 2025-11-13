package com.test.pulse.controller.boardnotice;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.mapper.BoardNoticeMapper;
import com.test.pulse.model.boardnotice.BoardNoticeDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boardnotice")
public class BoardNoticeController {
	
	private final BoardNoticeMapper mapper;
	
	@GetMapping("/list.do")
	public String list(Model model, @RequestParam(defaultValue="1") int page) {
		
		//한 페이지당 게시글 10개
		int pageSize = 10;
		int offset = (page - 1) * pageSize;
		
		//페이징 목록 가져오기
		List<BoardNoticeDTO> list = mapper.listPaging(offset, pageSize);
		
		//전체 게시물 수
		int totalCount = mapper.totalCount();
		
		//총 페이지 수
		int totalPage = (int)Math.ceil((double)totalCount / pageSize);
		
		
		model.addAttribute("list", mapper.list());
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		
		return "boardnotice.list";
	}
	
	@GetMapping("/view.do")
	public String view(Model model, int seq) {
		
		// 조회수 증가
	    mapper.increaseReadCount(seq);

	    // 다시 조회해서 모델에 담기
		model.addAttribute("dto", mapper.view(seq));
		
		return "boardnotice.view";
	}
	
	@GetMapping("/add.do")
	public String add() {
		
		return "boardnotice.add";
	}
	
	@PostMapping("/addok.do")
	public String addok(BoardNoticeDTO dto, @RequestParam("attach") MultipartFile attach, HttpServletRequest req) {
		
		try {
			if (attach != null && !attach.isEmpty()) {
				
				//파일 확장자 추출
				String originalName = attach.getOriginalFilename();
				String ext = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
				
				//파일 확장자 목록
				List<String> allowExt = Arrays.asList("jpg", "jpeg", "png", "gif");
				
				//확장자 목록에 없으면 파일 거부
				if (!allowExt.contains(ext)) {
					throw new RuntimeException("jpg, jpeg, png, gif 파일만 가능합니다." + ext);
				}
				
				//저장할 경로
				String path = req.getServletContext().getRealPath("/boardnoticefiles");
				File dir = new File(path);
				if (!dir.exists()) dir.mkdirs();
				
				//실제 파일명
				String filename = UUID.randomUUID().toString() + "_" + attach.getOriginalFilename();
				File target = new File(dir, filename);
				
				//저장
				attach.transferTo(target);
				
				//DB에 저장할 파일명
				dto.setAttach(filename);
				
			}
			
			mapper.add(dto);
			
			return "redirect:/boardnotice/list.do";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
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

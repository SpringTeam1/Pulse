package com.test.pulse.controller.boardnotice;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.pulse.mapper.BoardNoticeMapper;
import com.test.pulse.model.boardnotice.BoardNoticeDTO;

import lombok.RequiredArgsConstructor;

/**
 * 공지사항 게시판 관련 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/boardnotice")
public class BoardNoticeController {

    private final BoardNoticeMapper mapper;

    /**
     * 공지게시판 글 목록을 페이징하여 보여준다.
     *
     * @param model 뷰에 전달할 데이터를 담는 Model 객체
     * @param page  요청된 페이지 번호 (기본값: 1)
     * @return 공지사항 목록 뷰 이름
     */
    @GetMapping("/list.do")
    public String list(
            Model model,
            @RequestParam(defaultValue = "1") int page) {

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        List<BoardNoticeDTO> list = mapper.listPaging(offset, pageSize);
        int totalCount = mapper.totalCount();
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        
        return "boardnotice.list";
    }

    /**
     * 공지사항 상세 내용을 보여주고 조회수를 증가시킨다.
     *
     * @param model 뷰에 전달할 데이터를 담는 Model 객체
     * @param seq   조회할 공지사항의 번호
     * @return 공지사항 상세 뷰 이름
     */
    @GetMapping("/view.do")
    public String view(Model model, int seq) {

    	mapper.increaseReadCount(seq);
        model.addAttribute("dto", mapper.view(seq));

        return "boardnotice.view";
    }

    /**
     * 공지사항 작성 화면을 보여준다. (관리자만 접근 가능)
     *
     * @param model 뷰에 전달할 데이터를 담는 Model 객체
     * @return 공지사항 작성 뷰 이름
     */
    @GetMapping("/add.do")
    public String add(Model model) {

        return "boardnotice.add";
    }

    /**
     * 공지사항을 등록한다.
     *
     * @param dto     작성된 공지사항 정보를 담은 DTO 객체
     * @param attach  업로드된 첨부파일
     * @param req     HttpServletRequest 객체
     * @return 공지사항 목록으로 리다이렉트
     */
    @PostMapping("/addok.do")
    public String addok(BoardNoticeDTO dto,
                        @RequestParam("attach") MultipartFile attach,
                        HttpServletRequest req) {

        try {

            // 파일 업로드 처리
            if (attach != null && !attach.isEmpty()) {

                String originalName = attach.getOriginalFilename();
                String ext = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();

                List<String> allowExt = Arrays.asList("jpg", "jpeg", "png", "gif");
                if (!allowExt.contains(ext)) {
                    throw new RuntimeException("jpg, jpeg, png, gif 파일만 업로드 가능합니다.");
                }

                String path = req.getServletContext().getRealPath("/boardnoticefiles");
                File dir = new File(path);
                if (!dir.exists()) dir.mkdirs();

                String filename = UUID.randomUUID().toString() + "_" + originalName;
                File target = new File(dir, filename);

                attach.transferTo(target);

                dto.setAttach(filename);
                
                System.out.println("이미지 저장 경로: " + path);
                System.out.println("저장된 파일명: " + filename);
                
            } else {
            	
            	dto.setAttach("");
            	
            }

            mapper.add(dto);
            return "redirect:/boardnotice/list.do";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 공지사항 수정 화면을 보여준다.
     *
     * @param model 뷰에 전달할 데이터를 담는 Model 객체
     * @param seq   수정할 공지사항의 번호
     * @return 공지사항 수정 뷰 이름
     */
    @GetMapping("/edit.do")
    public String edit(Model model, int seq) {

        model.addAttribute("dto", mapper.view(seq));
        return "boardnotice.edit";
    }

    /**
     * 수정된 공지사항을 등록한다.
     *
     * @param dto     수정된 공지사항 정보를 담은 DTO 객체
     * @param session HttpSession 객체
     * @return 공지사항 목록으로 리다이렉트
     */
    @PostMapping("/editok.do")
    public String editok(BoardNoticeDTO dto, HttpSession session) {

        mapper.edit(dto);
        return "redirect:/boardnotice/list.do";
    }

    /**
     * 공지사항을 삭제한다.
     *
     * @param seq     삭제할 공지사항의 번호
     * @param session HttpSession 객체
     * @param rttr    RedirectAttributes 객체
     * @return 공지사항 목록으로 리다이렉트
     */
    @GetMapping("/del.do")
    public String del(int seq, HttpSession session, RedirectAttributes rttr) {

        mapper.del(seq);
        return "redirect:/boardnotice/list.do";
    }

}
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.pulse.mapper.BoardNoticeMapper;
import com.test.pulse.model.boardnotice.BoardNoticeDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boardnotice")
public class BoardNoticeController {

    private final BoardNoticeMapper mapper;

    /* -------------------------------------------
     * 🔥 (1) 테스트 로그인 화면
     * ------------------------------------------- */
    @GetMapping("/testlogin.do")
    public String testlogin() {
    	
        return "boardnotice.testlogin";  // testlogin.jsp 만들면 됨
        
    }

    /* -------------------------------------------
     * 🔥 (2) 테스트 로그인 처리
     *  - 일반/관리자 계정 체크
     *  - 세션에 저장
     * ------------------------------------------- */
    @PostMapping("/testloginok.do")
    public String testloginok(HttpServletRequest req,
                              @RequestParam String accountId,
                              @RequestParam String password,
                              Model model) {

        // 임시 계정 하드코딩 (DB 없어도 동작)
        if (accountId.equals("adminhong@naver.com") && password.equals("1234")) {

            // ⭐ 세션 생성
            HttpSession session = req.getSession();
            session.setAttribute("accountId", "adminhong@naver.com");
            session.setAttribute("nickname", "관리자");
            session.setAttribute("role", "관리자");

            return "redirect:/boardnotice/list.do";

        } else if (accountId.equals("userhong@naver.com") && password.equals("1234")) {

            HttpSession session = req.getSession();
            session.setAttribute("accountId", "userhong@naver.com");
            session.setAttribute("nickname", "일반회원");
            session.setAttribute("role", "일반");

            return "redirect:/boardnotice/list.do";

        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "boardnotice.testlogin";
        }
    }

    /* -------------------------------------------
     * 🔥 (3) 목록 + 페이징
     * ------------------------------------------- */
    @GetMapping("/list.do")
    public String list(Model model,
                       @RequestParam(defaultValue = "1") int page,
                       HttpSession session) {

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        List<BoardNoticeDTO> list = mapper.listPaging(offset, pageSize);
        int totalCount = mapper.totalCount();
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        
        //jsp에서도 pageSize사용가능 10 대신에 사용
        //model.addAttribute("pageSize", pageSize);

        // 현재 로그인 상태 전달
        model.addAttribute("role", session.getAttribute("role"));

        return "boardnotice.list";
    }

    /* -------------------------------------------
     * 🔥 (4) 상세 보기 + 조회수 증가 view.do
     * ------------------------------------------- */
    @GetMapping("/view.do")
    public String view(Model model, int seq, HttpSession session) {

        mapper.increaseReadCount(seq);
        model.addAttribute("dto", mapper.view(seq));
        model.addAttribute("role", session.getAttribute("role"));

        return "boardnotice.view";
    }

    /* -------------------------------------------
     * 🔥 (5) 글쓰기 화면 (관리자만 접근 허용) add.do
     * ------------------------------------------- */
    @GetMapping("/add.do")
    public String add(HttpSession session, Model model, RedirectAttributes rttr) {

        // 관리자만 접근 가능
        if (!"관리자".equals(session.getAttribute("role"))) {
        	rttr.addFlashAttribute("msg", "권한이 없습니다.");
            return "redirect:/boardnotice/list.do";
        }

        model.addAttribute("nickname", session.getAttribute("nickname"));
        model.addAttribute("accountId", session.getAttribute("accountId"));
        
        return "boardnotice.add";
    }

    /* -------------------------------------------
     * 🔥 (6) 글쓰기 처리
     * ------------------------------------------- */
    @PostMapping("/addok.do")
    public String addok(BoardNoticeDTO dto,
                        @RequestParam("attach") MultipartFile attach,
                        HttpServletRequest req,
                        HttpSession session) {

        try {

            // 공지글 = 타입 2번 고정
            dto.setBoardContentTypeSeq(2);

            // ❗ 로그인한 사용자 기준으로 작성자 넣기
            dto.setAccountId((String) session.getAttribute("accountId"));

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

    /* -------------------------------------------
     * 🔥 (7) 수정 화면
     * ------------------------------------------- */
    @GetMapping("/edit.do")
    public String edit(Model model, int seq, HttpSession session, RedirectAttributes rttr) {

        // 일반회원 접근 금지
        if (!"관리자".equals(session.getAttribute("role"))) {
        	rttr.addFlashAttribute("msg", "권한이 없습니다.");
            return "redirect:/boardnotice/list.do";
        }

        model.addAttribute("dto", mapper.view(seq));
        return "boardnotice.edit";
    }

    /* -------------------------------------------
     * 🔥 (8) 수정 처리
     * ------------------------------------------- */
    @PostMapping("/editok.do")
    public String editok(BoardNoticeDTO dto, HttpSession session) {

        if (!"관리자".equals(session.getAttribute("role"))) {
            return "redirect:/boardnotice/list.do";
        }

        mapper.edit(dto);
        return "redirect:/boardnotice/list.do";
    }

    /* -------------------------------------------
     * 🔥 (9) 삭제 처리
     * ------------------------------------------- */
    @GetMapping("/del.do")
    public String del(int seq, HttpSession session, RedirectAttributes rttr) {

        if (!"관리자".equals(session.getAttribute("role"))) {
        	rttr.addFlashAttribute("msg", "권한이 없습니다.");
            return "redirect:/boardnotice/list.do";
        }

        mapper.del(seq);
        return "redirect:/boardnotice/list.do";
    }

}

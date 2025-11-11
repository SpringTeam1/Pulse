package com.test.pulse.controller.crew;


import com.test.pulse.model.crew.BoardDTO;
import com.test.pulse.service.crew.CrewBoardService;
import com.test.pulse.service.crew.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("crewboard")
public class CrewBoardController {

    private final CrewService crewService;
    private final CrewBoardService crewBoardService;

    @GetMapping("/list")
    public String boardlist(HttpSession session, Model model) {
        String accountId = (String) session.getAttribute("accountId");


        //권한 기능 생기면 삭제예정
        if (accountId == null) {

            return "redirect:/test-login";
        }

        String crewSeq = crewService.getCrewSeq(accountId);
        String crewName = crewService.getCrewName(crewSeq);
        model.addAttribute("crewSeq", crewSeq);
        model.addAttribute("crewName", crewName);
        model.addAttribute("accountId", accountId);

        return "crewboard.list";
    }

    @GetMapping("/add")
    public String add(HttpSession session, Model model) {

        String accountId = (String) session.getAttribute("accountId");
        String crewSeq = crewService.getCrewSeq(accountId);
        String nickname = crewService.getAccountIdsNickname(accountId);
        //권한 기능 생기면 삭제예정
        if (accountId == null) {

            return "redirect:/test-login";
        }

        model.addAttribute("crewSeq", crewSeq);
        model.addAttribute("nickname", nickname);

        return "crewboard.add";
    }

    @GetMapping("/view")
    public String get(
            @RequestParam("boardContentSeq") String boardContentSeq,
            HttpSession session,
            Model model
    ) {
        String accountId = (String) session.getAttribute("accountId");

        BoardDTO dto = crewBoardService.get(boardContentSeq);

        // ✅ 파일 확장자 미리 계산해서 JSP로 전달
        String fileExt = null;
        if (dto.getAttach() != null && dto.getAttach().contains(".")) {
            fileExt = dto.getAttach()
                    .substring(dto.getAttach().lastIndexOf('.') + 1)
                    .toLowerCase();
        }

        model.addAttribute("board", dto);
        model.addAttribute("fileExt", fileExt); // ✅ JSP에서 바로 사용 가능

        return "crewboard.view";
    }

}



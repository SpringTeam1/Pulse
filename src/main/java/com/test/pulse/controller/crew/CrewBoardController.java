package com.test.pulse.controller.crew;


import com.test.pulse.model.crew.BoardDTO;
import com.test.pulse.model.user.CustomUser;
import com.test.pulse.service.crew.CrewBoardService;
import com.test.pulse.service.crew.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("crewboard")
public class CrewBoardController {

    private final CrewService crewService;
    private final CrewBoardService crewBoardService;

    @GetMapping("/list")
    public String boardlist(Authentication authentication, Model model) {
        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return "redirect:/customlogin";
        }

        String crewSeq = crewService.getCrewSeq(accountId);
        if (crewSeq == null) {
            return "redirect:/crewmain";
        }
        String crewName = crewService.getCrewName(crewSeq);
        model.addAttribute("crewSeq", crewSeq);
        model.addAttribute("crewName", crewName);
        model.addAttribute("accountId", accountId);

        return "script.crewboard.list";
    }

    @GetMapping("/add")
    public String add(Authentication authentication, Model model) {
        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return "redirect:/customlogin";
        }

        String crewSeq = crewService.getCrewSeq(accountId);
        if (crewSeq == null) {
            return "redirect:/crewmain";
        }
        String nickname = crewService.getAccountIdsNickname(accountId);

        model.addAttribute("crewSeq", crewSeq);
        model.addAttribute("nickname", nickname);
        model.addAttribute("accountId", accountId);

        return "script.crewboard.add";
    }

    @GetMapping("/view")
    public String get(
            @RequestParam("boardContentSeq") String boardContentSeq,
            Authentication authentication,
            Model model
    ) {
        String accountId = getAccountId(authentication);
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

        return "script.crewboard.view";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("boardContentSeq") String boardContentSeq,
                       Authentication authentication,
                       Model model) {
        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return "redirect:/customlogin";
        }

        BoardDTO bdto = crewBoardService.get(boardContentSeq);

        if( bdto == null ) {

            model.addAttribute("msg", "존재하지 않는 게시글입니다.");
            model.addAttribute("url", "/crewboard/list");
            return "alert";

        }

        if (!bdto.getAccountId().equals(accountId)) {
            model.addAttribute("msg", "본인 글만 수정할 수 있습니다.");
            model.addAttribute("url", "/crewboard/view/" + boardContentSeq);
            return "alert";
        }

        model.addAttribute("bdto", bdto);
        return "script.crewboard.edit"; // Tiles 경로
    }

    private String getAccountId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUser)) {
            return null;
        }
        return ((CustomUser) authentication.getPrincipal()).getAdto().getAccountId();
    }

}
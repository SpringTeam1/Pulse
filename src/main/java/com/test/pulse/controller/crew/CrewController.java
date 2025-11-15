package com.test.pulse.controller.crew;

import com.test.pulse.model.crew.CrewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pulse.service.crew.CrewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @GetMapping("/crewmain")
    public String showCrewMainPage(
            @RequestParam(value = "lat", required = false, defaultValue = "37.5665") double lat,
            @RequestParam(value = "lng", required = false, defaultValue = "126.9780") double lng,
            HttpServletRequest req, Model model) {

        String accountId = (String) req.getSession().getAttribute("accountId");

        boolean isInCrew = false;
        if (accountId != null) {
            isInCrew = crewService.isUserInCrew(accountId);
        }

        model.addAttribute("isInCrew", isInCrew);
        model.addAttribute("nearbyCrewList", crewService.getNearbyCrews(lat, lng));
        model.addAttribute("popularCrewList", crewService.getPopularCrews());
        return "script.crew.main";
    }


    @GetMapping("/crewregister")
    public String showCrewRegisterPage(){

        return "script.crew.register";
    }

    //리퀘스트 진입 시점에서 로그인체크 및 크루 가입여부 확인
    @GetMapping("/crewrequest")
    public String showCrewRequestPage(HttpSession session, Model model){
        String accountId = (String) session.getAttribute("accountId");

        if (accountId == null) {
            // 로그인페이지로 이동하게 만들어야함 현재는 테스트임
            // 나중에 security 에서 권한으로 조정할 예정
            return "redirect:/test-login";
        }

        String crewSeq = crewService.getCrewSeq(accountId);
        if (crewSeq == null) {
            return "redirect:/crewmain";
        }

        if(!crewService.isCrewLeader(accountId, crewSeq)){
            return  "redirect:/crewmain";
        }

        model.addAttribute("crewSeq", crewSeq);
        model.addAttribute("accountId", accountId);
        return "script.crew.request";
    }


    @GetMapping("/test-login")
    public String testLogin(HttpServletRequest req) {
        // ✅ DB에 실제 존재하는 accountId로 세션에 저장
        req.getSession().setAttribute("accountId", "test2@naver.com"); // 예: hong, user1 등
        req.getSession().setAttribute("nickname", "테스트유저");
        return "redirect:/crewmain"; // 로그인 후 가고 싶은 페이지로 리다이렉트
    }

    @GetMapping("/crewview")
    public String showCrewView(@RequestParam("crewSeq") String crewSeq,
                               HttpServletRequest req,
                               Model model) {

        String accountId = (String) req.getSession().getAttribute("accountId");
        model.addAttribute("dto", crewService.getCrew(crewSeq));

        // ✅ 이미 가입되어 있는지 여부
        boolean isUserInCrew = false;
        if (accountId != null) {
            isUserInCrew = crewService.isUserInCrew(accountId);
        }

        model.addAttribute("accountId", accountId);
        model.addAttribute("isUserInCrew", isUserInCrew);
        return "script.crew.view";
    }

    @GetMapping("/crewdashboard")
    public String crewDashBoard(
            HttpServletRequest req,
            Model model
    ) {

        String accountId = (String) req.getSession().getAttribute("accountId");
        String crewSeq =  crewService.getCrewSeq(accountId);
        CrewDTO crewdto = crewService.getCrew(crewSeq);
        Boolean isLeader = crewService.isCrewLeader(accountId, crewSeq);
        String attach = crewdto.getCrewAttach();
        String nickname = crewService.getAccountIdsNickname(accountId);
        String profile = "/crewboardFile/default.png";

        model.addAttribute("profileUrl", profile);
        model.addAttribute("nickname", nickname);
        model.addAttribute("accountId", accountId);
        model.addAttribute("crewSeq", crewSeq);
        model.addAttribute("isLeader", isLeader);
        model.addAttribute("attach", attach);
        return "script.crew.dashboard";
    }

}
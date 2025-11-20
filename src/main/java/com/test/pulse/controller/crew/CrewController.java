package com.test.pulse.controller.crew;

import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.model.user.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pulse.service.crew.CrewService;

/**
 * 크루 관련 요청을 처리하는 컨트롤러 클래스
 */
@Controller
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    /**
     * 크루 메인 페이지를 반환한다.
     *
     * @param lat            현재 위도
     * @param lng            현재 경도
     * @param authentication 현재 사용자 인증 정보
     * @param model          뷰에 전달할 데이터를 담는 Model 객체
     * @return 크루 메인 페이지 뷰 이름
     */
    @GetMapping("/crewmain")
    public String showCrewMainPage(@RequestParam(value = "lat", required = false, defaultValue = "37.5665") double lat,
                                   @RequestParam(value = "lng", required = false, defaultValue = "126.9780") double lng,
                                   Authentication authentication,
                                   Model model) {

        String accountId = getAccountId(authentication);
        boolean isInCrew = accountId != null && crewService.isUserInCrew(accountId);
        System.out.println("isInCrew: " + isInCrew);
        System.out.println("accountId: " + accountId);

        model.addAttribute("isInCrew", isInCrew);
        model.addAttribute("nearbyCrewList", crewService.getNearbyCrews(lat, lng));
        model.addAttribute("popularCrewList", crewService.getPopularCrews());
        model.addAttribute("accountId", accountId);
        return "script.crew.main";
    }

    /**
     * 크루 등록 페이지를 반환한다.
     *
     * @return 크루 등록 페이지 뷰 이름
     */
    @GetMapping("/crewregister")
    public String showCrewRegisterPage() {

        return "script.crew.register";
    }

    /**
     * 크루 가입 요청 관리 페이지를 반환한다.
     *
     * @param authentication 현재 사용자 인증 정보
     * @param model          뷰에 전달할 데이터를 담는 Model 객체
     * @return 크루 가입 요청 관리 페이지 뷰 이름
     */
    @GetMapping("/crewrequest")
    public String showCrewRequestPage(Authentication authentication, Model model){
        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return "redirect:/customlogin";
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

    /**
     * 크루 상세 정보 페이지를 반환한다.
     *
     * @param crewSeq        조회할 크루 번호
     * @param authentication 현재 사용자 인증 정보
     * @param model          뷰에 전달할 데이터를 담는 Model 객체
     * @return 크루 상세 정보 페이지 뷰 이름
     */
    @GetMapping("/crewview")
    public String showCrewView(@RequestParam("crewSeq") String crewSeq,
                               Authentication authentication,
                               Model model) {

        String accountId = getAccountId(authentication);
        model.addAttribute("dto", crewService.getCrew(crewSeq));

        // 이미 가입되어 있는지 여부
        boolean isUserInCrew = false;
        if (accountId != null) {
            isUserInCrew = crewService.isUserInCrew(accountId);
        }

        model.addAttribute("accountId", accountId);
        model.addAttribute("isUserInCrew", isUserInCrew);
        return "script.crew.view";
    }

    /**
     * 크루 대시보드 페이지를 반환한다.
     *
     * @param authentication 현재 사용자 인증 정보
     * @param model          뷰에 전달할 데이터를 담는 Model 객체
     * @return 크루 대시보드 페이지 뷰 이름
     */
    @GetMapping("/crewdashboard")
    public String crewDashBoard(
            Authentication authentication,
            Model model
    ) {

        String accountId = getAccountId(authentication);
        if (accountId == null) {
            return "redirect:/customlogin";
        }

        String crewSeq =  crewService.getCrewSeq(accountId);
        if (crewSeq == null) {
            return "redirect:/crewmain";
        }

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

    /**
     * Authentication 객체에서 사용자 계정 ID를 추출한다.
     *
     * @param authentication 현재 사용자 인증 정보
     * @return 사용자 계정 ID, 없으면 null
     */
    private String getAccountId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUser)) {
            return null;
        }
        return ((CustomUser) authentication.getPrincipal()).getAdto().getAccountId();
    }

}

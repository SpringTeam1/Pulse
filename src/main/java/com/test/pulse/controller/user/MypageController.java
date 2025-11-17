package com.test.pulse.controller.user;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.model.user.AccountInfoDTO;
import com.test.pulse.service.user.InfoEditService;

@Controller
public class MypageController {
	
	@GetMapping("/mypage")
	public String mypage(Model model) {
		
		String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

	    AccountInfoDTO updated = infoEditService.get(accountId);         // 기본 정보
	    AccountInfoDTO updatedDetail = infoEditService.getDetail(accountId); // 상세 정보

	    // 필요한 데이터만 꺼내서 세팅
	    updated.setName(updatedDetail.getName());
	    updated.getNickname();
	    updated.setPhoneNum(updatedDetail.getPhoneNum());
	    updated.setBirthday(updatedDetail.getBirthday());
	    updated.setGender(updatedDetail.getGender());
	    updated.setRegionCity(updatedDetail.getRegionCity());
	    updated.setRegionCounty(updatedDetail.getRegionCounty());
	    updated.setRegionDistrict(updatedDetail.getRegionDistrict());
	    updated.setExerciseFrequency(updatedDetail.getExerciseFrequency());

	    model.addAttribute("adto", updated);
	    model.addAttribute("now", System.currentTimeMillis());
		
		return "user.mypage";
	}
	
	@GetMapping("/infoedit")
	public String infoedit(Model model) {
		
		String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

	    AccountInfoDTO updated = infoEditService.get(accountId);         // 기본 정보
	    AccountInfoDTO updatedDetail = infoEditService.getDetail(accountId); // 상세 정보

	    // 필요한 데이터만 꺼내서 세팅
	    updated.setName(updatedDetail.getName());
	    updated.getNickname();
	    updated.setPhoneNum(updatedDetail.getPhoneNum());
	    updated.setBirthday(updatedDetail.getBirthday());
	    updated.setGender(updatedDetail.getGender());
	    updated.setRegionCity(updatedDetail.getRegionCity());
	    updated.setRegionCounty(updatedDetail.getRegionCounty());
	    updated.setRegionDistrict(updatedDetail.getRegionDistrict());
	    updated.setExerciseFrequency(updatedDetail.getExerciseFrequency());

	    model.addAttribute("adto", updated);
	    model.addAttribute("now", System.currentTimeMillis());
	    
	    System.out.println("Nickname = " + updated.getNickname());
		
		return "script.user.infoedit";
	}
	
	@Autowired
	private InfoEditService infoEditService;
	
	@PostMapping("/infoedit")
	public String infoeditok(@ModelAttribute AccountInfoDTO dto,
			@RequestParam("photoFile") MultipartFile photoFile,
		    @RequestParam("photoFile") MultipartFile profilePhoto,
            @RequestParam String yyyy,
            @RequestParam String mm,
            @RequestParam String dd,
            HttpSession session,
            HttpServletRequest req,
            Model model) {
		
		String saveDirPath = req.getServletContext().getRealPath("/asset/pic");
		System.out.println("이미지 저장 경로: " + saveDirPath);
		File saveDir = new File(saveDirPath);
		if (!saveDir.exists()) saveDir.mkdirs();

		String filename = dto.getProfilePhoto();

		if (profilePhoto != null && !profilePhoto.isEmpty()) {
		    String ct = profilePhoto.getContentType();
		    boolean isImage = ct != null && (ct.equalsIgnoreCase("image/png")
		                                  || ct.equalsIgnoreCase("image/jpeg")
		                                  || ct.equalsIgnoreCase("image/jpg"));

		    if (isImage) {
		    	
		    	if (filename != null && !filename.equals("pic.png")) {
	                File old = new File(saveDir, filename);
	                if (old.exists()) old.delete();
	            }
		    	
		        // 원래 파일명을 제거하고, 확장자만 유지
		    	String originalFilename = profilePhoto.getOriginalFilename();
		    	String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		        
		        // 새 파일명 생성 (현재 시간 기반 + 확장자)
		        filename = System.currentTimeMillis() + ext;
		        File dest = new File(saveDir, filename);

		        try {
		            profilePhoto.transferTo(dest);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		}
		
		// 생년월일 조합
	    dto.setBirthday(String.format("%s-%02d-%02d", yyyy, Integer.parseInt(mm), Integer.parseInt(dd)));
	    dto.setAccountId(SecurityContextHolder.getContext().getAuthentication().getName());
	    
	    dto.setProfilePhoto(filename);

	    int result1 = infoEditService.update(dto);
	    int result2 = infoEditService.updateDetail(dto);

	    if (result1 == 1 && result2 == 1) {
	        // 수정 성공
	        return "redirect:/mypage";
	    }

	    // 실패 시 처리 (선택)
	    model.addAttribute("error", "수정 실패");
	    return "user/infoedit";
	}
	
	@GetMapping("/pwchange")
	public String pwchange() {
		
		return "user.pwchange";
	}
	
	@GetMapping("/myactivities")
	public String myactivities() {
		
		return "user.myactivities";
	}
	
	@GetMapping("/deleteaccount")
	public String deleteaccount() {
		
		return "user.deleteaccount";
	}
	
	@PostMapping("/deleteaccountok")
	public String deleteaccountok(HttpSession session) {
		
		String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

	    AccountInfoDTO dto = new AccountInfoDTO();
	    dto.setAccountId(accountId);
	    
	    dto.setBirthday(null);

	    infoEditService.deleteaccount(dto);
	    infoEditService.deleteaccountDetail(dto);

	    session.invalidate(); //세션 종료
		
		return "redirect:/index";
	}

}

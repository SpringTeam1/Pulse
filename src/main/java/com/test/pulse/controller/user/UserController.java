package com.test.pulse.controller.user;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;
import com.test.pulse.service.user.MailService;
import com.test.pulse.service.user.VerificationService;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 관련 요청을 처리하는 컨트롤러 클래스(회원가입 등)
 */
@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserMapper mapper;
	private final BCryptPasswordEncoder encoder;
	private final MailService mailService;
	private final VerificationService verificationService;
	
	/**
	 * 회원가입 유형 선택 페이지를 반환한다.
	 *
	 * @return 회원가입 유형 선택 페이지 뷰 이름
	 */
	@GetMapping("/registerselect")
	public String registerselect() {
		
		return "user.registerselect";
	}
	
	/**
	 * 회원가입 페이지를 반환한다.
	 *
	 * @param registerType 회원가입 유형
	 * @param req          HttpServletRequest 객체
	 * @return 회원가입 페이지 뷰 이름
	 */
	@GetMapping("/register")
	public String register(@RequestParam(required=false) String registerType, HttpServletRequest req) {
		 
		req.setAttribute("registerType", registerType);
		
		return "script.user.register";
	}
	
	/**
	 * 회원가입을 처리한다.
	 *
	 * @param adto         회원 정보를 담은 DTO 객체
	 * @param profilePhoto 프로필 사진 파일
	 * @param req          HttpServletRequest 객체
	 * @return 회원가입 완료 페이지 뷰 이름
	 */
	@Transactional
	@PostMapping("/registerok")
	public String registerok(AccountInfoDTO adto, @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            HttpServletRequest req) {
		
		if (!"true".equalsIgnoreCase(req.getParameter("emailVerified"))) {
			// 인증 안 했으면 다시 폼으로
			req.setAttribute("emailMsg", "이메일 인증이 필요한다.");
			return "user.register";
		}
		
		adto.setPassword(encoder.encode(adto.getPassword()));
		
		if ((adto.getBirthday()==null || adto.getBirthday().isBlank())
		        && req.getParameter("yyyy")!=null) {
		        String y = req.getParameter("yyyy");
		        String m = String.format("%02d", Integer.parseInt(req.getParameter("mm")));
		        String d = String.format("%02d", Integer.parseInt(req.getParameter("dd")));
		        adto.setBirthday(y + "-" + m + "-" + d);
		}
		
		
		//이미지 업로드
		String saveDirPath = req.getServletContext().getRealPath("/asset/pic");
		System.out.println("이미지 저장 경로: " + saveDirPath);
		File saveDir = new File(saveDirPath);
		if (!saveDir.exists()) saveDir.mkdirs();

		String filename = "pic.png"; // 기본 이미지

		if (profilePhoto != null && !profilePhoto.isEmpty()) {
		    String ct = profilePhoto.getContentType();
		    boolean isImage = ct != null && (ct.equalsIgnoreCase("image/png")
		                                  || ct.equalsIgnoreCase("image/jpeg")
		                                  || ct.equalsIgnoreCase("image/jpg"));

		    if (isImage) {
		    	
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


		
		adto.setProfilePhoto(filename);
			
		mapper.add(adto);
		mapper.addDetail(adto);
		
		return "user.registerok";
		
	}

}
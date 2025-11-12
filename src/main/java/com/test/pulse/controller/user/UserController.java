package com.test.pulse.controller.user;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;
import com.test.pulse.service.user.MailService;
import com.test.pulse.service.user.VerificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserMapper mapper;
	private final BCryptPasswordEncoder encoder;
	private final MailService mailService;
	private final VerificationService verificationService;
	
	@GetMapping("/registerselect")
	public String registerselect() {
		
		return "user.registerselect";
	}
	
	@GetMapping("/register")
	public String register(@RequestParam(required=false) String registerType, HttpServletRequest req) {
		 
		req.setAttribute("registerType", registerType);
		
		return "script.user.register";
	}
	
	@Transactional
	@PostMapping("/registerok")
	public String registerok(AccountInfoDTO adto, @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            HttpServletRequest req) {
		
		if (!"true".equalsIgnoreCase(req.getParameter("emailVerified"))) {
			// 인증 안 했으면 다시 폼으로
			req.setAttribute("emailMsg", "이메일 인증이 필요합니다.");
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
		
		mapper.add(adto);
		mapper.addDetail(adto);
		
		String saveDirPath = req.getServletContext().getRealPath("/asset/pic");
        File saveDir = new File(saveDirPath);
        if (!saveDir.exists()) saveDir.mkdirs();

        File targetFile = new File(saveDir, "pic.png"); // 최종 저장 파일

        // 2) 파일 업로드가 있는 경우만 처리
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String ct = profilePhoto.getContentType(); // MIME 타입 검사

            // PNG/JPG 외 거절
            boolean png = "image/png".equalsIgnoreCase(ct);
            boolean jpg = "image/jpeg".equalsIgnoreCase(ct) || "image/jpg".equalsIgnoreCase(ct);

            if (png || jpg) {
                try {
					profilePhoto.transferTo(targetFile);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 그냥 그대로 저장
            }
        }
		
		return "user.registerok";
	}

	

}

package com.test.pulse.controller.user;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserMapper mapper;
	
	@GetMapping("/registerselect")
	public String registerselect() {
		
		return "user.registerselect";
	}
	
	@GetMapping("/register")
	public String register() {
		 
		return "user.register";
	}
	
	@PostMapping("/registerok")
	public String registerok(AccountInfoDTO adto, @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            HttpServletRequest req) {
		
		//dto.setPassword(encoder.encode(dto.getMemberpw()));
		mapper.add(adto);
		
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
	
	@PostMapping(value="/api/nick/check", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> checkNick(@RequestParam String nickname){
	    boolean dup = UserMapper.countByNickname(nickname) > 0;
	    return Map.of("duplicate", dup);
	}

	

}

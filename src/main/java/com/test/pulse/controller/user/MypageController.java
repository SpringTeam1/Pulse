package com.test.pulse.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
	
	@GetMapping("/mypage")
	public String mypage() {
		
		return "user.mypage";
	}
	
	@GetMapping("/infoedit")
	public String infoedit() {
		
		return "user.infoedit";
	}
	
	@GetMapping("/myactivities")
	public String myactivities() {
		
		return "user.myactivities";
	}
	
	@GetMapping("/deleteaccount")
	public String deleteaccount() {
		
		return "user.deleteaccount";
	}

}

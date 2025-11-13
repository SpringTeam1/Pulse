package com.test.pulse.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MypageController {
	
	@GetMapping("/mypage")
	public String mypage() {
		
		return "user.mypage";
	}
	
	@GetMapping("/infoedit")
	public String infoedit() {
		
		return "script.user.infoedit";
	}
	
	@PostMapping("/infoedit")
	public String infoeditok() {
		
		return "redirect:/mypage";
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

}

package com.test.pulse.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@GetMapping("/accesserror")
	public String accesserror() {
		
		return "user.accesserror";
	}
	
	@GetMapping("/customlogin")
	public String customlogin() {
		
		return "user.auth/customlogin";
	}
	
	@GetMapping("/customlogout")
	public String customlogout() {
		
		return "user.auth/customlogout";
	}
	
}






















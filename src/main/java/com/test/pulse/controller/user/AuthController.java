package com.test.pulse.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

	@GetMapping("/accesserror")
	public String accesserror() {
		
		return "user.accesserror";
	}
	
	@GetMapping("/customlogin")
	public String customlogin() {
		
		return "user.customlogin";
	}
	
	@GetMapping("/customlogout")
	public String customlogout() {
		
		return "user.customlogout";
	}
	
	@GetMapping("/index")
	public String index() {
		
		return "index";
	}
	
}

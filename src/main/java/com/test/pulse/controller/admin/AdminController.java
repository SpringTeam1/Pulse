package com.test.pulse.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/header")
	public String header() {
		
		
		return "inc/header";
	}
}

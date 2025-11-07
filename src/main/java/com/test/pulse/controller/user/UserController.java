package com.test.pulse.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.AccountInfoDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserMapper mapper;
	
	@GetMapping("/registor.do")
	public String register() {
		
		return "register";
	}
	
	@PostMapping("/registorok.do")
	public String registorok(AccountInfoDTO adto) {
		
		mapper.add(adto);
		
		return "registorok";
	}
	

}

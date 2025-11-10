package com.test.pulse.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String registerok(AccountInfoDTO adto) {
		
		//dto.setPassword(encoder.encode(dto.getMemberpw()));
		mapper.add(adto);
		
		
		
		return "user.registerok";
	}
	

}

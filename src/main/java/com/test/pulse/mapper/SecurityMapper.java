package com.test.pulse.mapper;

import com.test.pulse.model.user.AccountInfoDTO;

public interface SecurityMapper {
	
	//@Select("")
	String time();

	void add(AccountInfoDTO dto);

}




package com.test.pulse.model;

import com.test.pulse.model.user.AccountInfoDTO;

public interface SecurityMapper {
	
	//@Select("")
	String time();

	void add(AccountInfoDTO dto);

}




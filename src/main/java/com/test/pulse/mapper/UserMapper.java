package com.test.pulse.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.test.pulse.model.user.AccountInfoDTO;

@Mapper
public interface UserMapper {

	void add(AccountInfoDTO adto);

	void addDetail(AccountInfoDTO dto);

	AccountInfoDTO get(String username);
	
	void update(AccountInfoDTO adto);
	
	void updateDetail(AccountInfoDTO adto);
	
}

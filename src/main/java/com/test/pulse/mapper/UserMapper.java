package com.test.pulse.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.test.pulse.model.user.AccountInfoDTO;

@Mapper
public interface UserMapper {

	void add(AccountInfoDTO adto);

	void addDetail(AccountInfoDTO dto);

	AccountInfoDTO get(String username);
	
	AccountInfoDTO getDetail(String username);
	
	int update(AccountInfoDTO adto);
	
	int updateDetail(AccountInfoDTO adto);
	
	void deleteaccount(AccountInfoDTO adto);
	
	void deleteaccountDetail(AccountInfoDTO adto);
	
}

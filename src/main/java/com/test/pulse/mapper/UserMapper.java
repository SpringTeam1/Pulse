package com.test.pulse.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.test.pulse.model.AccountInfoDTO;

@Mapper
public interface UserMapper {

	void add(AccountInfoDTO adto);

	void addDetail(AccountInfoDTO dto);
	
}

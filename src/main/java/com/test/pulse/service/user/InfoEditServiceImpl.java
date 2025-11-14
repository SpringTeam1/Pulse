package com.test.pulse.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;

@Service
public class InfoEditServiceImpl implements InfoEditService {
	
    @Autowired
    private UserMapper userMapper;

    public int update(AccountInfoDTO dto) {
        return userMapper.update(dto);
    }

    public int updateDetail(AccountInfoDTO dto) {
        return userMapper.updateDetail(dto);
    }
    
    public void deleteaccount(AccountInfoDTO dto) {
        userMapper.deleteaccount(dto);
    }

    public void deleteaccountDetail(AccountInfoDTO dto) {
        userMapper.deleteaccountDetail(dto);
    }
    
    public AccountInfoDTO get(String accountId) {
        return userMapper.get(accountId);
    }

    public AccountInfoDTO getDetail(String accountId) {
        return userMapper.getDetail(accountId);
    }


}

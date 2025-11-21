package com.test.pulse.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.pulse.mapper.UserMapper;
import com.test.pulse.model.user.AccountInfoDTO;

/**
 * InfoEditService 인터페이스의 구현 클래스
 */
@Service
public class InfoEditServiceImpl implements InfoEditService {
	
    @Autowired
    private UserMapper userMapper;

    /**
     * 회원 계정 정보를 수정한다.
     * @param dto 수정할 회원 정보
     * @return 수정된 행의 수
     */
    public int update(AccountInfoDTO dto) {
        return userMapper.update(dto);
    }

    /**
     * 회원 상세 정보를 수정한다.
     * @param dto 수정할 회원 상세 정보
     * @return 수정된 행의 수
     */
    public int updateDetail(AccountInfoDTO dto) {
        return userMapper.updateDetail(dto);
    }
    
    /**
     * 회원 계정을 삭제한다.
     * @param dto 삭제할 회원 정보
     */
    public void deleteaccount(AccountInfoDTO dto) {
        userMapper.deleteaccount(dto);
    }

    /**
     * 회원 상세 정보를 삭제한다.
     * @param dto 삭제할 회원 상세 정보
     */
    public void deleteaccountDetail(AccountInfoDTO dto) {
        userMapper.deleteaccountDetail(dto);
    }
    
    /**
     * 회원 계정 정보를 조회한다.
     * @param accountId 조회할 회원 ID
     * @return 회원 계정 정보
     */
    public AccountInfoDTO get(String accountId) {
        return userMapper.get(accountId);
    }

    /**
     * 회원 상세 정보를 조회한다.
     * @param accountId 조회할 회원 ID
     * @return 회원 상세 정보
     */
    public AccountInfoDTO getDetail(String accountId) {
        return userMapper.getDetail(accountId);
    }


}

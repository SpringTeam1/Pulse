package com.test.pulse.service.user;

import com.test.pulse.model.user.AccountInfoDTO;

/**
 * 회원 정보 수정 관련 서비스 인터페이스
 */
public interface InfoEditService {

    /**
     * 회원 계정 정보를 수정한다.
     * @param dto 수정할 회원 정보
     * @return 수정된 행의 수
     */
    int update(AccountInfoDTO dto);

    /**
     * 회원 상세 정보를 수정한다.
     * @param dto 수정할 회원 상세 정보
     * @return 수정된 행의 수
     */
    int updateDetail(AccountInfoDTO dto);

    /**
     * 회원 계정을 삭제한다.
     * @param dto 삭제할 회원 정보
     */
    void deleteaccount(AccountInfoDTO dto);

    /**
     * 회원 상세 정보를 삭제한다.
     * @param dto 삭제할 회원 상세 정보
     */
    void deleteaccountDetail(AccountInfoDTO dto);

    /**
     * 회원 계정 정보를 조회한다.
     * @param accountId 조회할 회원 ID
     * @return 회원 계정 정보
     */
    AccountInfoDTO get(String accountId);

    /**
     * 회원 상세 정보를 조회한다.
     * @param accountId 조회할 회원 ID
     * @return 회원 상세 정보
     */
    AccountInfoDTO getDetail(String accountId);

}

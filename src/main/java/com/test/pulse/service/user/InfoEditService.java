package com.test.pulse.service.user;

import com.test.pulse.model.user.AccountInfoDTO;

public interface InfoEditService {

	int update(AccountInfoDTO dto);
    int updateDetail(AccountInfoDTO dto);
    void deleteaccount(AccountInfoDTO dto);
    void deleteaccountDetail(AccountInfoDTO dto);
	AccountInfoDTO get(String accountId);
	AccountInfoDTO getDetail(String accountId);

}

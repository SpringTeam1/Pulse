package com.test.pulse.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.test.pulse.model.user.AccountInfoDTO;

/**
 * 사용자 계정(`tblAccountInfo`, `tblAccountDetail`)에 대한 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
@Mapper
public interface UserMapper {

	/**
     * 새로운 사용자의 기본 계정 정보를 등록한다. (`tblAccountInfo`)
     *
     * @param adto 등록할 사용자의 기본 계정 정보를 담은 DTO
     */
	void add(AccountInfoDTO adto);

	/**
     * 새로운 사용자의 상세 정보를 등록한다. (`tblAccountDetail`)
     *
     * @param dto 등록할 사용자의 상세 정보를 담은 DTO
     */
	void addDetail(AccountInfoDTO dto);

	/**
     * 지정된 사용자 이름(ID)에 해당하는 기본 계정 정보를 조회한다.
     *
     * @param username 조회할 사용자의 이름(ID)
     * @return 조회된 사용자의 기본 계정 정보 DTO
     */
	AccountInfoDTO get(String username);
	
	/**
     * 지정된 사용자 이름(ID)에 해당하는 상세 정보를 조회한다.
     *
     * @param username 조회할 사용자의 이름(ID)
     * @return 조회된 사용자의 상세 정보 DTO
     */
	AccountInfoDTO getDetail(String username);
	
	/**
     * 기존 사용자의 기본 계정 정보를 수정한다.
     *
     * @param adto 수정할 사용자의 기본 계정 정보를 담은 DTO
     * @return 영향을 받은 행의 수
     */
	int update(AccountInfoDTO adto);
	
	/**
     * 기존 사용자의 상세 정보를 수정한다.
     *
     * @param adto 수정할 사용자의 상세 정보를 담은 DTO
     * @return 영향을 받은 행의 수
     */
	int updateDetail(AccountInfoDTO adto);
	
	/**
     * 사용자의 기본 계정을 삭제(또는 비활성화)한다.
     *
     * @param adto 삭제할 사용자의 계정 정보를 담은 DTO
     */
	void deleteaccount(AccountInfoDTO adto);
	
	/**
     * 사용자의 상세 정보를 삭제한다.
     *
     * @param adto 삭제할 사용자의 계정 정보를 담은 DTO
     */
	void deleteaccountDetail(AccountInfoDTO adto);
	
}

package com.test.pulse.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountInfoDTO {
	
	private String accountId;
	private String password;
	private String profilePhoto;
	private String nickname;
	private String accountRole;
	private String accountSeq;
	private String accountLevel;
	private String registerType;
	private String accountCategory;
	
	private String accountInfoDetailSeq;
	private String name;
	private String phoneNum;
	private String birthday;
	private String gender;
	private String regionCity;
	private String regionCounty;
	private String regionDistrict;
	private String exerciseFrequency;
	private String joinDate;

}

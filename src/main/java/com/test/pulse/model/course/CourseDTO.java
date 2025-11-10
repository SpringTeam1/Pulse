package com.test.pulse.model.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CourseDTO {
	private String courseSeq;
	private String courseName;
	private String accountId;
	private String courseApproval;
	private String description;
	private String courseLength;
	private String startAddress;
	private String endAddress;
	private String trackData;
	private String regdate;
}

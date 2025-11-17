package com.test.pulse.model.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GPXCourseDTO {
	private int courseSeq;
	private String courseName;
	private String accountId;
	private String courseApproval;
	private String description;
	private double courseLength;
	private String startAddress;
	private String endAddress;
	private String trackData;
	private String regdate;
}

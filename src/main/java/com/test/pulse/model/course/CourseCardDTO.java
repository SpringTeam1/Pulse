package com.test.pulse.model.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CourseCardDTO {
	private int courseSeq;
	private String courseName;
	private double courseLength;
	private String startAddress;
	private String endAddress;
	private int favoriteCount; // 즐겨찾기 수 (목록 카드용)
	private String accountId;
}

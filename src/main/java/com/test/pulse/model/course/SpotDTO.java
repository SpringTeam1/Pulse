package com.test.pulse.model.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SpotDTO {
	private String spotSeq;
	private String place;
	private String lat;
	private String lng;
	private String regionCity;
	private String regionCounty;
	private String regionDistrict;
	private String courseSeq;
	private int spotStep;
}

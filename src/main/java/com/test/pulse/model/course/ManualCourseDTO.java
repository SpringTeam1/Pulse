package com.test.pulse.model.course;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ManualCourseDTO {
	private List<CoordinateDTO> coords;
	private String courseName;
	private String description;
	private String accountId;
}

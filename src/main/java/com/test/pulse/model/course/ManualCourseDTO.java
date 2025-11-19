package com.test.pulse.model.course;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@ApiModel(description = "수동 코스 등록 요청 데이터")
public class ManualCourseDTO {
	@ApiModelProperty(value = "사용자 입력 좌표 리스트", example = "[{\"lat\":37.56884608817254,\"lon\":126.95852585544836},{\"lat\":37.57087159948117,\"lon\":126.99909442644896}]")
	private List<CoordinateDTO> coords;
	
	@ApiModelProperty(value = "코스 이름", example = "남산 산책로")
	private String courseName;
	
	@ApiModelProperty(value = "코스 설명", example = "오르막길이 많습니다.")
	private String description;
	
	@ApiModelProperty(hidden=true)
	private String accountId;
}

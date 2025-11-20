package com.test.pulse.model.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "지도 좌표 정보 (위도, 경도)")
@Getter @Setter @ToString
public class CoordinateDTO {

    @ApiModelProperty(value = "위도 (Latitude)", required = true, example = "37.5665")
	private double lat;

    @ApiModelProperty(value = "경도 (Longitude)", required = true, example = "126.9780")
	private double lon;
}

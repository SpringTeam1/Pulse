package com.test.pulse.model.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 위도와 경도로 구성된 하나의 지리적 좌표를 나타내는 데이터 전송 객체(DTO)
 */
@ApiModel(description = "지도 좌표 정보 (위도, 경도)")
@Getter @Setter @ToString
public class CoordinateDTO {

    /**
     * 위도 (Latitude)
     */
    @ApiModelProperty(value = "위도 (Latitude)", required = true, example = "37.5665")
	private double lat;

    /**
     * 경도 (Longitude)
     */
    @ApiModelProperty(value = "경도 (Longitude)", required = true, example = "126.9780")
	private double lon;
}

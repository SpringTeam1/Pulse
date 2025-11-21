package com.test.pulse.model.mainpage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 메인 페이지에 표시될 날씨 및 코스 추천 정보를 담는 데이터 전송 객체(DTO)
 */
@ApiModel(description = "메인 페이지 날씨 및 코스 추천 정보")
@Getter
@Setter
@ToString
public class MainPageDTO {
    
    /**
     * 현재 기온 (단위: ℃)
     */
    @ApiModelProperty(value = "현재 기온 (℃)", example = "24.5")
	private double temp;

    /**
     * 현재 습도 (단위: %)
     */
    @ApiModelProperty(value = "습도 (%)", example = "60")
    private int humidity;

    /**
     * 강수 확률 (단위: %)
     */
    @ApiModelProperty(value = "강수 확률 (%)", example = "10")
    private int rainChance;

    /**
     * 하늘 상태 (예: 맑음, 구름많음, 흐림)
     */
    @ApiModelProperty(value = "하늘 상태 (맑음, 흐림 등)", example = "맑음")
    private String skyCondition;

    /**
     * 강수 형태 (예: 없음, 비, 비/눈, 눈)
     */
    @ApiModelProperty(value = "강수 형태 (비, 눈, 없음 등)", example = "없음")
    private String precipitationType;
    
}

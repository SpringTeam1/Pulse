package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "크루 기본 정보")
@Data
public class CrewDTO {

    @ApiModelProperty(value = "크루 고유 번호", hidden = true)
    private String crewSeq;

    @ApiModelProperty(value = "크루 이름", required = true, example = "한강 러닝 크루")
    private String crewName;

    @ApiModelProperty(value = "크루 설명", required = true, example = "매주 화요일 저녁 8시 정기 러닝합니다.")
    private String description;

    @ApiModelProperty(value = "현재 멤버 수", hidden = true)
    private int memberCount;

    @ApiModelProperty(value = "활동 지역 (시/도)", example = "서울시")
    private String regionCity;

    @ApiModelProperty(value = "활동 지역 (시/군/구)", example = "마포구")
    private String regionCounty;

    @ApiModelProperty(value = "활동 지역 (읍/면/동)", example = "상암동")
    private String regionDistrict;

    @ApiModelProperty(value = "크루 대표의 계정 ID", hidden = true)
    private String accountId;

    @ApiModelProperty(value = "크루 대표 이미지 파일명", hidden = true)
    private String crewAttach;

    @ApiModelProperty(value = "활동 지역 위도", example = "37.5665")
    private double latitude;

    @ApiModelProperty(value = "활동 지역 경도", example = "126.9780")
    private double longitude;

    @ApiModelProperty(value = "사용자 위치로부터의 거리 (km)", hidden = true)
    private Double distance;

    @ApiModelProperty(value = "크루 대표의 닉네임", hidden = true)
    private String nickname;

}

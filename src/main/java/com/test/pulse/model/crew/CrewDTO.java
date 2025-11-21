package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 크루의 이름, 설명, 위치, 멤버 정보 등 주요 정보를 담는 데이터 전송 객체(DTO)
 */
@ApiModel(description = "크루 기본 정보")
@Data
public class CrewDTO {

    /**
     * 크루의 고유 번호 (Primary Key)
     */
    @ApiModelProperty(value = "크루 고유 번호", hidden = true)
    private String crewSeq;

    /**
     * 크루의 이름
     */
    @ApiModelProperty(value = "크루 이름", required = true, example = "한강 러닝 크루")
    private String crewName;

    /**
     * 크루에 대한 상세 설명 및 활동 내용
     */
    @ApiModelProperty(value = "크루 설명", required = true, example = "매주 화요일 저녁 8시 정기 러닝합니다.")
    private String description;

    /**
     * 현재 크루에 속한 멤버의 수
     */
    @ApiModelProperty(value = "현재 멤버 수", hidden = true)
    private int memberCount;

    /**
     * 주요 활동 지역 (시/도)
     */
    @ApiModelProperty(value = "활동 지역 (시/도)", example = "서울시")
    private String regionCity;

    /**
     * 주요 활동 지역 (시/군/구)
     */
    @ApiModelProperty(value = "활동 지역 (시/군/구)", example = "마포구")
    private String regionCounty;

    /**
     * 주요 활동 지역 (읍/면/동)
     */
    @ApiModelProperty(value = "활동 지역 (읍/면/동)", example = "상암동")
    private String regionDistrict;

    /**
     * 크루를 생성한 크루 대표(리더)의 계정 ID
     */
    @ApiModelProperty(value = "크루 대표의 계정 ID", hidden = true)
    private String accountId;

    /**
     * 크루의 대표 이미지 파일명
     */
    @ApiModelProperty(value = "크루 대표 이미지 파일명", hidden = true)
    private String crewAttach;

    /**
     * 주요 활동 지역의 위도
     */
    @ApiModelProperty(value = "활동 지역 위도", example = "37.5665")
    private double latitude;

    /**
     * 주요 활동 지역의 경도
     */
    @ApiModelProperty(value = "활동 지역 경도", example = "126.9780")
    private double longitude;

    /**
     * 현재 사용자의 위치로부터 크루 활동 지역까지의 계산된 거리 (단위: km)
     * 주로 주변 크루 검색 시 사용
     */
    @ApiModelProperty(value = "사용자 위치로부터의 거리 (km)", hidden = true)
    private Double distance;

    /**
     * 크루 대표(리더)의 닉네임
     */
    @ApiModelProperty(value = "크루 대표의 닉네임", hidden = true)
    private String nickname;

}

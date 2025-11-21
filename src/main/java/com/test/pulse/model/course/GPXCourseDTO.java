package com.test.pulse.model.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * GPX 파일로부터 생성되었거나 수동으로 등록된 코스의 상세 정보를 담는 데이터 전송 객체(DTO)
 * 코스의 메타데이터와 경로 정보 등을 포함
 */
@ApiModel(description = "GPX 코스 상세 정보")
@Getter @Setter @ToString
public class GPXCourseDTO {
    /**
     * 코스의 고유 번호 (Primary Key)
     */
    @ApiModelProperty(value = "코스 고유 번호", hidden = true)
	private int courseSeq;

    /**
     * 코스의 이름
     */
    @ApiModelProperty(value = "코스 이름", example = "한강 공원 러닝 코스")
	private String courseName;

    /**
     * 코스를 등록한 사용자의 계정 ID
     */
    @ApiModelProperty(value = "작성자 ID", hidden = true)
	private String accountId;

    /**
     * 코스의 관리자 승인 상태
     */
    @ApiModelProperty(value = "코스 승인 상태", hidden = true)
	private String courseApproval;

    /**
     * 코스에 대한 상세 설명
     */
    @ApiModelProperty(value = "코스 설명", example = "초보자도 뛰기 좋은 평지 코스입니다.")
	private String description;

    /**
     * 코스의 총 길이 (단위: km)
     */
    @ApiModelProperty(value = "코스 총 거리 (km)", example = "5.2")
	private double courseLength;

    /**
     * 코스 시작 지점의 주소
     */
    @ApiModelProperty(value = "시작 지점 주소", example = "서울시 마포구 상암동")
	private String startAddress;

    /**
     * 코스 종료 지점의 주소
     */
    @ApiModelProperty(value = "종료 지점 주소", example = "서울시 영등포구 여의도동")
	private String endAddress;

    /**
     * 코스의 경로 정보 (좌표 리스트를 담은 JSON 형태의 문자열)
     */
    @ApiModelProperty(value = "GPX 트랙 데이터 (JSON)")
	private String trackData;

    /**
     * 코스 등록일
     */
    @ApiModelProperty(value = "등록일", hidden = true)
	private String regdate;
}

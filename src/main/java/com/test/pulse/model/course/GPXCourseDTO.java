package com.test.pulse.model.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "GPX 코스 상세 정보")
@Getter @Setter @ToString
public class GPXCourseDTO {
    @ApiModelProperty(value = "코스 고유 번호", hidden = true)
	private int courseSeq;

    @ApiModelProperty(value = "코스 이름", example = "한강 공원 러닝 코스")
	private String courseName;

    @ApiModelProperty(value = "작성자 ID", hidden = true)
	private String accountId;

    @ApiModelProperty(value = "코스 승인 상태 (Y/N)", hidden = true)
	private String courseApproval;

    @ApiModelProperty(value = "코스 설명", example = "초보자도 뛰기 좋은 평지 코스입니다.")
	private String description;

    @ApiModelProperty(value = "코스 총 거리 (km)", example = "5.2")
	private double courseLength;

    @ApiModelProperty(value = "시작 지점 주소", example = "서울시 마포구 상암동")
	private String startAddress;

    @ApiModelProperty(value = "종료 지점 주소", example = "서울시 영등포구 여의도동")
	private String endAddress;

    @ApiModelProperty(value = "GPX 트랙 데이터 (JSON)")
	private String trackData;

    @ApiModelProperty(value = "등록일", hidden = true)
	private String regdate;
}

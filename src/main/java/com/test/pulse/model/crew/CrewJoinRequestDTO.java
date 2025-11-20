package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "크루 가입 신청 정보")
@Data
public class CrewJoinRequestDTO {

    @ApiModelProperty(value = "가입 신청 고유 번호", hidden = true)
    private String crewJoinSeq;

    @ApiModelProperty(value = "신청 대상 크루 번호", required = true, example = "1")
    private String crewSeq;

    @ApiModelProperty(value = "신청자 ID", hidden = true)
    private String accountId;

    @ApiModelProperty(value = "신청 상태 (대기, 승인, 거절)", hidden = true)
    private String requestState;

    @ApiModelProperty(value = "신청자 닉네임", hidden = true)
    private String nickname;
}



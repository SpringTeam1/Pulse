package com.test.pulse.model.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 사용자의 크루 가입 신청 정보를 담는 데이터 전송 객체(DTO)
 */
@ApiModel(description = "크루 가입 신청 정보")
@Data
public class CrewJoinRequestDTO {

    /**
     * 크루 가입 신청의 고유 번호 (Primary Key)
     */
    @ApiModelProperty(value = "가입 신청 고유 번호", hidden = true)
    private String crewJoinSeq;

    /**
     * 가입을 신청한 크루의 고유 번호
     */
    @ApiModelProperty(value = "신청 대상 크루 번호", required = true, example = "1")
    private String crewSeq;

    /**
     * 가입을 신청한 사용자의 계정 ID
     */
    @ApiModelProperty(value = "신청자 ID", hidden = true)
    private String accountId;

    /**
     * 가입 신청의 현재 상태 (예: PENDING, APPROVED, REJECTED)
     */
    @ApiModelProperty(value = "신청 상태 (대기, 승인, 거절)", hidden = true)
    private String requestState;

    /**
     * 가입을 신청한 사용자의 닉네임
     */
    @ApiModelProperty(value = "신청자 닉네임", hidden = true)
    private String nickname;
}

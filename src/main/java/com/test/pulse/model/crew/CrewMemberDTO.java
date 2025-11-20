package com.test.pulse.model.crew;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "크루원 정보")
@Data
public class CrewMemberDTO {
    @ApiModelProperty(value = "멤버 고유 번호", hidden = true)
    private String crewMemberSeq;

    @ApiModelProperty(value = "소속 크루 번호", example = "1")
    private String crewSeq;

    @ApiModelProperty(value = "멤버 계정 ID", hidden = true)
    private String accountId;

    @ApiModelProperty(value = "크루 직급(리더/크루원)", example = "MEMBER")
    private String crewGrade;

    @ApiModelProperty(value = "멤버 닉네임", example = "러닝초보")
    private String nickname;
}

package com.test.pulse.model.crew;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 크루에 속한 한 명의 멤버 정보를 담는 데이터 전송 객체(DTO)
 * 멤버의 역할, 닉네임 등의 정보를 포함
 */
@ApiModel(description = "크루원 정보")
@Data
public class CrewMemberDTO {
    /**
     * 크루 멤버의 고유 번호 (Primary Key)
     */
    @ApiModelProperty(value = "멤버 고유 번호", hidden = true)
    private String crewMemberSeq;

    /**
     * 멤버가 속한 크루의 고유 번호
     */
    @ApiModelProperty(value = "소속 크루 번호", example = "1")
    private String crewSeq;

    /**
     * 멤버의 사용자 계정 ID
     */
    @ApiModelProperty(value = "멤버 계정 ID", hidden = true)
    private String accountId;

    /**
     * 크루 내에서의 멤버 등급 (예: LEADER, MEMBER)
     */
    @ApiModelProperty(value = "크루 직급(리더/크루원)", example = "MEMBER")
    private String crewGrade;

    /**
     * 멤버의 닉네임
     */
    @ApiModelProperty(value = "멤버 닉네임", example = "러닝초보")
    private String nickname;
}

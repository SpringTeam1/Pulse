package com.test.pulse.model.crew;


import lombok.Data;

@Data
public class CrewMemberDTO {
    private String crewMemberSeq;
    private String crewSeq;
    private String accountId;
    private String crewGrade;
    private String nickname;
}

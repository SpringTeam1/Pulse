package com.test.pulse.model.crew;

import lombok.Data;

@Data
public class CrewDTO {

    private String crewSeq;
    private String crewName;
    private String description;
    private int memberCount;
    private String regionCity;
    private String regionCounty;
    private String regionDistrict;
    private String accountId;
    private String crewAttach;
    private double latitude;
    private double longitude;
    private Double distance;
    private String nickname;

}

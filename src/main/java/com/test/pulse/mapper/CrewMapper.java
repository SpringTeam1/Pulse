package com.test.pulse.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.model.crew.CrewJoinRequestDTO;

@Mapper
public interface CrewMapper {

    int add(CrewDTO dto);

    CrewDTO get(@Param("crewSeq") String crewSeq);

    boolean isUserInCrew(@Param("accountId") String accountId);

    String getCrewSeq(@Param("accountId") String accountId);

    List<CrewDTO> listPopular();

    List<CrewDTO> listNearby(@Param("lat") double lat, @Param("lng") double lng);

    List<CrewDTO> crewList();

    int addCrewJoinRequest(@Param("crewSeq") String crewSeq, @Param("accountId") String accountId);

    boolean isCrewLeader(@Param("accountId") String accountId, @Param("crewSeq") String crewSeq);

    List<CrewJoinRequestDTO> getJoinRequestsByCrewSeq(@Param("crewSeq") String crewSeq);

    int approveJoinRequest(@Param("crewJoinSeq") String crewJoinSeq);

    int rejectJoinRequest(@Param("crewJoinSeq") String crewJoinSeq);
}

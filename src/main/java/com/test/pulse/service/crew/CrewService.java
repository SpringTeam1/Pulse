package com.test.pulse.service.crew;

import com.test.pulse.mapper.CrewMapper;
import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.model.crew.CrewJoinRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewMapper mapper;

    public int addCrew(CrewDTO dto) {
        return mapper.add(dto);
    }

    public CrewDTO getCrew(String crewSeq) {
        return mapper.get(crewSeq);
    }

    public boolean isUserInCrew(String accountId) {
        return mapper.isUserInCrew(accountId);
    }

    public String getCrewSeq(String accountId) {
        return mapper.getCrewSeq(accountId);
    }

    public List<CrewDTO> getPopularCrews() {
        return mapper.listPopular();
    }

    public List<CrewDTO> getNearbyCrews(double lat, double lng) {
        return mapper.listNearby(lat, lng);
    }

    public List<CrewDTO> getAllCrews() {
        return mapper.crewList();
    }

    public int requestJoinCrew(String crewSeq, String accountId) {
        return mapper.addCrewJoinRequest(crewSeq, accountId);
    }

    public boolean isCrewLeader(String accountId, String crewSeq) {
        return mapper.isCrewLeader(accountId, crewSeq);
    }

    public List<CrewJoinRequestDTO> getJoinRequests(String crewSeq) {
        return mapper.getJoinRequestsByCrewSeq(crewSeq);
    }

    public int approveJoin(String crewJoinSeq) {
        return mapper.approveJoinRequest(crewJoinSeq);
    }

    public int rejectJoin(String crewJoinSeq) {
        return mapper.rejectJoinRequest(crewJoinSeq);
    }
}
package com.test.pulse.service.crew;

import com.test.pulse.mapper.CrewMapper;
import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.model.crew.CrewJoinRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewMapper mapper;

    @Transactional
    public int addCrew(CrewDTO dto) {
        int result = mapper.add(dto);
        if (result == 0) throw new RuntimeException("크루 생성 실패");

        Map<String, Object> map = new HashMap<>();
        map.put("crewSeq", dto.getCrewSeq());
        map.put("accountId", dto.getAccountId());
        mapper.addCrewLeader(map);

        mapper.upCountCrewMember(dto.getCrewSeq());

        return 1;
    }

    public CrewDTO getCrew(String crewSeq) {
        return mapper.get(crewSeq);
    }

    public boolean isUserInCrew(String accountId) {
        return mapper.isUserInCrew(accountId) > 0; // ✅ true / false로 반환
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

    public boolean isAlreadyRequested(String crewSeq, String accountId) { return  mapper.isAlreadyRequested(crewSeq, accountId); }

    public boolean isCrewLeader(String accountId, String crewSeq) {
        return mapper.isCrewLeader(accountId, crewSeq) == 1;
    }

    public List<CrewJoinRequestDTO> getJoinRequests(String crewSeq) {
        return mapper.getJoinRequestsByCrewSeq(crewSeq);
    }

    @Transactional
    public int approveJoin(String crewJoinSeq) {

        int result = mapper.approveJoinRequest(crewJoinSeq);
        if (result == 0) throw new RuntimeException("가입 신청 실패");

        String accountId = mapper.getAccountIdByCrewJoinSeq(crewJoinSeq);
        String crewSeq = mapper.getCrewSeqByCrewJoinSeq(crewJoinSeq);

        Map<String, Object> map = new HashMap<>();
        map.put("crewSeq", crewSeq);
        map.put("accountId", accountId);
        mapper.addCrewMember(map);

        mapper.upCountCrewMember(crewSeq);

        return 1;
    }

    public int upCountCrewMember(String crewSeq) { return mapper.upCountCrewMember(crewSeq);}

    public int rejectJoin(String crewJoinSeq) {
        return mapper.rejectJoinRequest(crewJoinSeq);
    }


    public String getCrewName(String crewSeq) {
        return mapper.getCrewName(crewSeq);
    }

    public String getAccountIdsNickname(String accountId) {
        return mapper.getAccountIdsNickname(accountId);
    }
}
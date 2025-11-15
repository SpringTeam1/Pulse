package com.test.pulse.service.crew;

import com.test.pulse.mapper.CrewMapper;
import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.model.crew.CrewJoinRequestDTO;
import com.test.pulse.model.crew.CrewMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewMapper mapper;
    private final RestTemplate restTemplate;

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

    public List<CrewMemberDTO> memberList(String crewSeq){
        return mapper.memberList(crewSeq);
    }

    public String getMarathon(){

        String serviceKey ="b4daefc37c80997b0b0834b1b5f2e3d83e95045b55b659015a56bb2a13cadb97";
        String url = "https://api.odcloud.kr/api/15138980/v1/uddi:eedc77c5-a56b-4e77-9c1d-9396fa9cc1d3?page=1&perPage=1&serviceKey="+serviceKey;

        return restTemplate.getForObject(url, String.class);

    }
}
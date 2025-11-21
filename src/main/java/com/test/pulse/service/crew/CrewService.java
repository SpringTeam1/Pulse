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

/**
 * 크루 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewMapper mapper;
    private final RestTemplate restTemplate;

    /**
     * 새로운 크루를 추가하고, 생성자를 크루장으로 등록한다.
     * @param dto 추가할 크루 정보
     * @return 추가 결과
     */
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

    /**
     * 크루 정보를 조회한다.
     * @param crewSeq 조회할 크루 번호
     * @return 크루 정보
     */
    public CrewDTO getCrew(String crewSeq) {
        return mapper.get(crewSeq);
    }

    /**
     * 사용자가 이미 크루에 가입되어 있는지 확인한다.
     * @param accountId 사용자 ID
     * @return 가입 여부
     */
    public boolean isUserInCrew(String accountId) {
        return mapper.isUserInCrew(accountId) > 0; // ✅ true / false로 반환
    }

    /**
     * 주어진 사용자의 크루 시퀀스 번호를 조회한다.
     * @param accountId 사용자 ID
     * @return 크루 시퀀스 번호
     */
    public String getCrewSeq(String accountId) {
        return mapper.getCrewSeq(accountId);
    }

    /**
     * 인기 크루 목록을 조회한다.
     * @return 인기 크루 목록
     */
    public List<CrewDTO> getPopularCrews() {
        return mapper.listPopular();
    }

    /**
     * 위도와 경도를 기반으로 주변 크루 목록을 조회한다.
     * @param lat 위도
     * @param lng 경도
     * @return 주변 크루 목록
     */
    public List<CrewDTO> getNearbyCrews(double lat, double lng) {
        return mapper.listNearby(lat, lng);
    }

    /**
     * 모든 크루 목록을 조회한다.
     * @return 모든 크루 목록
     */
    public List<CrewDTO> getAllCrews() {
        return mapper.crewList();
    }

    /**
     * 새로운 크루 가입 요청을 생성한다.
     * @param crewSeq 크루 번호
     * @param accountId 사용자 ID
     * @return 생성 결과
     */
    public int requestJoinCrew(String crewSeq, String accountId) {
        return mapper.addCrewJoinRequest(crewSeq, accountId);
    }

    /**
     * 사용자가 특정 크루에 이미 가입 요청을 했는지 확인한다.
     * @param crewSeq 크루 번호
     * @param accountId 사용자 ID
     * @return 요청 여부
     */
    public boolean isAlreadyRequested(String crewSeq, String accountId) { return  mapper.isAlreadyRequested(crewSeq, accountId); }

    /**
     * 사용자가 특정 크루의 크루장인지 확인한다.
     * @param accountId 사용자 ID
     * @param crewSeq 크루 번호
     * @return 크루장 여부
     */
    public boolean isCrewLeader(String accountId, String crewSeq) {
        return mapper.isCrewLeader(accountId, crewSeq) == 1;
    }

    /**
     * 특정 크루의 가입 요청 목록을 조회한다.
     * @param crewSeq 크루 번호
     * @return 가입 요청 목록
     */
    public List<CrewJoinRequestDTO> getJoinRequests(String crewSeq) {
        return mapper.getJoinRequestsByCrewSeq(crewSeq);
    }

    /**
     * 가입 요청을 승인하고, 사용자를 크루 멤버로 추가하며, 멤버 수를 업데이트한다.
     * @param crewJoinSeq 가입 요청 번호
     * @return 승인 결과
     */
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

    /**
     * 크루 멤버 수를 1 증가시킨다.
     * @param crewSeq 크루 번호
     * @return 업데이트 결과
     */
    public int upCountCrewMember(String crewSeq) { return mapper.upCountCrewMember(crewSeq);}

    /**
     * 크루 가입 요청을 거절한다.
     * @param crewJoinSeq 가입 요청 번호
     * @return 거절 결과
     */
    public int rejectJoin(String crewJoinSeq) {
        return mapper.rejectJoinRequest(crewJoinSeq);
    }


    /**
     * 크루의 이름을 조회한다.
     * @param crewSeq 크루 번호
     * @return 크루 이름
     */
    public String getCrewName(String crewSeq) {
        return mapper.getCrewName(crewSeq);
    }

    /**
     * 사용자의 닉네임을 조회한다.
     * @param accountId 사용자 ID
     * @return 사용자 닉네임
     */
    public String getAccountIdsNickname(String accountId) {
        return mapper.getAccountIdsNickname(accountId);
    }

    /**
     * 특정 크루의 멤버 목록을 조회한다.
     * @param crewSeq 크루 번호
     * @return 멤버 목록
     */
    public List<CrewMemberDTO> memberList(String crewSeq){
        return mapper.memberList(crewSeq);
    }

    /**
     * 외부 API로부터 마라톤 정보를 조회한다.
     * @return 마라톤 정보
     */
    public String getMarathon(){

        String serviceKey ="b4daefc37c80997b0b0834b1b5f2e3d83e95045b55b659015a56bb2a13cadb97";
        String url = "https://api.odcloud.kr/api/15138980/v1/uddi:eedc77c5-a56b-4e77-9c1d-9396fa9cc1d3?page=1&perPage=1&serviceKey="+serviceKey;

        return restTemplate.getForObject(url, String.class);

    }
}
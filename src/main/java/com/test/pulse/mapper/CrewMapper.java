package com.test.pulse.mapper;

import java.util.List;
import java.util.Map;

import com.test.pulse.model.crew.CrewMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.test.pulse.model.crew.CrewDTO;
import com.test.pulse.model.crew.CrewJoinRequestDTO;
import org.springframework.security.core.parameters.P;

/**
 * 크루(`tblCrew`), 크루 멤버(`tblCrewMember`), 가입 요청(`tblCrewJoinRequest`) 등
 * 크루 관련 모든 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
@Mapper
public interface CrewMapper {

    /**
     * 새로운 크루를 생성한다.
     *
     * @param dto 생성할 크루 정보를 담은 DTO
     * @return 영향을 받은 행의 수
     */
    int add(CrewDTO dto);

    /**
     * 지정된 번호(PK)에 해당하는 크루의 상세 정보를 조회한다.
     *
     * @param crewSeq 조회할 크루의 고유 번호(PK)
     * @return 조회된 크루 DTO
     */
    CrewDTO get(@Param("crewSeq") String crewSeq);

    /**
     * 특정 사용자가 어떤 크루에 속해 있는지 확인한다.
     *
     * @param accountId 확인할 사용자의 계정 ID
     * @return 크루에 속해 있으면 1, 아니면 0
     */
    int isUserInCrew(@Param("accountId") String accountId);

    /**
     * 특정 사용자가 속한 크루의 고유 번호(PK)를 조회한다.
     *
     * @param accountId 크루 번호를 조회할 사용자의 계정 ID
     * @return 해당 사용자가 속한 크루의 고유 번호
     */
    String getCrewSeq(@Param("accountId") String accountId);

    /**
     * 인기 크루 목록을 조회한다.
     *
     * @return 인기 크루 DTO 리스트
     */
    List<CrewDTO> listPopular();

    /**
     * 지정된 위도와 경도 근처에 있는 크루 목록을 조회한다.
     *
     * @param lat 위도
     * @param lng 경도
     * @return 주변 크루 DTO 리스트
     */
    List<CrewDTO> listNearby(@Param("lat") double lat, @Param("lng") double lng);

    /**
     * 전체 크루 목록을 조회한다.
     *
     * @return 전체 크루 DTO 리스트
     */
    List<CrewDTO> crewList();

    /**
     * 크루 가입 요청을 생성한다.
     *
     * @param crewSeq   가입을 요청할 크루의 고유 번호(PK)
     * @param accountId 가입을 요청하는 사용자의 계정 ID
     * @return 영향을 받은 행의 수
     */
    int addCrewJoinRequest(@Param("crewSeq") String crewSeq, @Param("accountId") String accountId);

    /**
     * 특정 사용자가 특정 크루에 이미 가입 요청을 했는지 확인한다.
     *
     * @param crewSeq   확인할 크루의 고유 번호(PK)
     * @param accountId 확인할 사용자의 계정 ID
     * @return 이미 요청했다면 true, 아니면 false
     */
    Boolean isAlreadyRequested(@Param("crewSeq") String crewSeq, @Param("accountId") String accountId);

    /**
     * 특정 크루의 멤버 수를 1 증가시킨다.
     *
     * @param crewSeq 멤버 수를 증가시킬 크루의 고유 번호(PK)
     * @return 영향을 받은 행의 수
     */
    int upCountCrewMember(@Param("crewSeq") String crewSeq );

    /**
     * 특정 사용자가 특정 크루의 리더인지 확인한다.
     *
     * @param accountId 확인할 사용자의 계정 ID
     * @param crewSeq   확인할 크루의 고유 번호(PK)
     * @return 크루 리더이면 1, 아니면 0
     */
    int isCrewLeader(@Param("accountId") String accountId, @Param("crewSeq") String crewSeq);

    /**
     * 특정 크루에 접수된 모든 가입 요청 목록을 조회한다.
     *
     * @param crewSeq 가입 요청을 조회할 크루의 고유 번호(PK)
     * @return 해당 크루의 가입 요청 DTO 리스트
     */
    List<CrewJoinRequestDTO> getJoinRequestsByCrewSeq(@Param("crewSeq") String crewSeq);

    /**
     * 크루 가입 요청을 승인한다.
     *
     * @param crewJoinSeq 승인할 가입 요청의 고유 번호(PK)
     * @return 영향을 받은 행의 수
     */
    int approveJoinRequest(@Param("crewJoinSeq") String crewJoinSeq);

    /**
     * 크루 가입 요청을 거절한다.
     *
     * @param crewJoinSeq 거절할 가입 요청의 고유 번호(PK)
     * @return 영향을 받은 행의 수
     */
    int rejectJoinRequest(@Param("crewJoinSeq") String crewJoinSeq);

    /**
     * 특정 크루의 이름을 조회한다.
     *
     * @param crewSeq 크루 이름을 조회할 크루의 고유 번호(PK)
     * @return 해당 크루의 이름
     */
    String getCrewName(@Param("crewSeq") String crewSeq);

    /**
     * 특정 사용자의 닉네임을 조회한다.
     *
     * @param accountId 닉네임을 조회할 사용자의 계정 ID
     * @return 해당 사용자의 닉네임
     */
    String getAccountIdsNickname(@Param("accountId")  String accountId);

    /**
     * 사용자를 크루의 리더로 등록한다. (tblCrewMember에 추가)
     *
     * @param map 'accountId', 'crewSeq'를 포함하는 Map
     */
    void addCrewLeader(Map<String, Object> map);

    /**
     * 사용자를 크루의 멤버로 등록한다. (tblCrewMember에 추가)
     *
     * @param map 'accountId', 'crewSeq'를 포함하는 Map
     */
    void addCrewMember(Map<String, Object> map);

    /**
     * 가입 요청 번호(PK)를 통해 요청한 사용자의 계정 ID를 조회한다.
     *
     * @param crewJoinSeq 가입 요청의 고유 번호(PK)
     * @return 요청한 사용자의 계정 ID
     */
    String getAccountIdByCrewJoinSeq(@Param("crewJoinSeq") String crewJoinSeq);


    /**
     * 가입 요청 번호(PK)를 통해 요청된 크루의 고유 번호(PK)를 조회한다.
     *
     * @param crewJoinSeq 가입 요청의 고유 번호(PK)
     * @return 요청된 크루의 고유 번호
     */
    String getCrewSeqByCrewJoinSeq(@Param("crewJoinSeq") String crewJoinSeq);

    /**
     * 특정 크루의 모든 멤버 목록을 조회한다.
     *
     * @param crewSeq 멤버 목록을 조회할 크루의 고유 번호(PK)
     * @return 해당 크루의 멤버 DTO 리스트
     */
    List<CrewMemberDTO> memberList(@Param("crewSeq")String crewSeq);
}

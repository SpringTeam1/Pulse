package com.test.pulse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.pulse.model.workout.WorkoutDTO;
import com.test.pulse.model.workout.WorkoutLogDTO;

/**
 * 운동 기록(`tblWorkout`)에 대한 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
@Mapper
public interface WorkoutMapper {

	/**
     * 새로운 운동 기록을 데이터베이스에 등록한다.
     *
     * @param log 등록할 운동 기록 정보를 담은 DTO
     */
	void insertWorkout(WorkoutDTO log);

	/**
     * 특정 사용자의 모든 운동 기록 목록을 조회한다.
     *
     * @param accountId 운동 기록을 조회할 사용자의 계정 ID
     * @return 해당 사용자의 운동 기록 DTO 리스트
     */
	List<WorkoutLogDTO> getWorkoutsByAccountId(String accountId);

	/**
     * 지정된 번호(PK)에 해당하는 운동 기록의 상세 정보를 조회한다.
     *
     * @param workoutSeq 조회할 운동 기록의 고유 번호(PK)
     * @return 조회된 운동 기록의 상세 정보 DTO
     */
	WorkoutDTO getWorkoutDetail(int workoutSeq);
	
}

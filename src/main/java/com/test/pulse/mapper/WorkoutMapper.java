package com.test.pulse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.pulse.model.workout.WorkoutDTO;
import com.test.pulse.model.workout.WorkoutLogDTO;

@Mapper
public interface WorkoutMapper {

	void insertWorkout(WorkoutDTO log);

	List<WorkoutLogDTO> getWorkoutsByAccountId(String accountId);
	
}

package com.test.pulse.model.workout;

import java.time.LocalDate;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter @Setter @ToString
public class WorkoutDTO {

	private int exerciseRecordSeq;
	private String accountId;
	private String trackData;
	
	private long totalTime;
	private double totalDistance;
	private int totalCalories;
	private double avgPace;
	
	private LocalDate workoutDate;
	private String attachment;
	private String exerciseComment;
	
}

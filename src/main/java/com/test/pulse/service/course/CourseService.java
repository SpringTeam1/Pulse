package com.test.pulse.service.course;

import org.springframework.stereotype.Service;

import com.test.pulse.mapper.CourseMapper;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class CourseService {
	private final CourseMapper courseMapper; //주입
	
	
}

package com.test.pulse.mapper;

import java.util.List;
import java.util.Map;

import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.model.course.GPXCourseDTO;

public interface CourseMapper {

	void insertCourse(GPXCourseDTO course);

	GPXCourseDTO getCourseDetail(int courseSeq);

	List<CourseCardDTO> getAllCourses(Map<String, Object> params);

	int getTotalCourseCount(Map<String, Object> params);

	List<CourseCardDTO> getPopularCourses(int count);
	
	//getRecommendedCourses
	
}

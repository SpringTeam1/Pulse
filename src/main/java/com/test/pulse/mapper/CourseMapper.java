package com.test.pulse.mapper;

import com.test.pulse.model.course.GPXCourseDTO;

public interface CourseMapper {

	void insertCourse(GPXCourseDTO course);

	GPXCourseDTO getCourseDetail(int courseSeq);
	
}

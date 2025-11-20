package com.test.pulse.mapper;

import java.util.List;
import java.util.Map;

import com.test.pulse.model.course.CourseCardDTO;
import com.test.pulse.model.course.GPXCourseDTO;

/**
 * 운동 코스(`tblCourse`)에 대한 데이터베이스 작업을 처리하는 MyBatis 인터페이스 매퍼
 */
public interface CourseMapper {

	/**
     * 새로운 코스를 데이터베이스에 등록한다.
     *
     * @param course 등록할 코스 정보를 담은 DTO
     */
	void insertCourse(GPXCourseDTO course);

	/**
     * 지정된 번호(PK)에 해당하는 코스의 상세 정보를 조회한다.
     *
     * @param courseSeq 조회할 코스의 고유 번호(PK)
     * @return 조회된 코스의 상세 정보 DTO
     */
	GPXCourseDTO getCourseDetail(int courseSeq);

	/**
     * 필터링, 정렬, 페이징 조건을 적용하여 코스 목록을 조회한다.
     *
     * @param params 필터, 정렬, 페이징 관련 파라미터를 담은 Map
     * @return 조회된 코스 카드 DTO 리스트
     */
	List<CourseCardDTO> getAllCourses(Map<String, Object> params);

	/**
     * 지정된 필터 조건에 맞는 전체 코스의 수를 조회한다.
     *
     * @param params 필터 관련 파라미터를 담은 Map
     * @return 조건에 맞는 총 코스 수
     */
	int getTotalCourseCount(Map<String, Object> params);

	/**
     * 지정된 수만큼 인기 코스 목록을 조회한다.
     *
     * @param count 조회할 인기 코스의 수
     * @return 인기 코스 카드 DTO 리스트
     */
	List<CourseCardDTO> getPopularCourses(int count);

	/**
     * 지정된 사용자의 위치 정보를 조회한다.
     *
     * @param accountId 사용자의 계정 ID
     * @return 사용자의 위치 정보를 담은 Map
     */
	Map<String, String> getUserLocation(String accountId);

	/**
     * 지정된 조건에 따라 사용자에게 추천되는 코스 목록을 조회한다.
     *
     * @param params 추천 코스 조회를 위한 파라미터를 담은 Map
     * @return 추천 코스 카드 DTO 리스트
     */
	List<CourseCardDTO> getRecommendedCourses(Map<String, Object> params);
	
	
	
}

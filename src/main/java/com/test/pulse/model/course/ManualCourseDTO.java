package com.test.pulse.model.course;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자가 수동으로 코스를 등록할 때 사용하는 데이터 전송 객체(DTO)
 * 지도에서 직접 클릭한 좌표 리스트와 코스 정보를 담는다.
 */
@Getter @Setter @ToString
@ApiModel(description = "수동 코스 등록 요청 데이터")
public class ManualCourseDTO {
	
	/**
     * 사용자가 지도에 입력한 좌표(위도, 경도)의 리스트
     */
	@ApiModelProperty(value = "사용자 입력 좌표 리스트(JSON)", example = "[{\"lat\":37.56884608817254,\"lon\":126.95852585544836},{\"lat\":37.57087159948117,\"lon\":126.99909442644896}]")
	private List<CoordinateDTO> coords;
	
	/**
     * 등록할 코스의 이름
     */
	@ApiModelProperty(value = "코스 이름", example = "남산 산책로")
	private String courseName;
	
	/**
     * 코스에 대한 설명
     */
	@ApiModelProperty(value = "코스 설명", example = "오르막길이 많습니다.")
	private String description;
	
	/**
     * 코스를 등록하는 사용자의 계정 ID
     * 이 값은 컨트롤러 또는 서비스 계층에서 세션 정보 등을 통해 자동으로 설정
     */
	@ApiModelProperty(hidden=true)
	private String accountId;
}

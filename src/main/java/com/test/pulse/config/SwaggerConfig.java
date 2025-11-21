package com.test.pulse.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Springfox Swagger 2 설정을 위한 클래스
 * 이 클래스는 API 문서를 자동으로 생성하고 UI를 통해 쉽게 탐색할 수 있도록 설정한다.
 *
 * @see EnableSwagger2
 */
@EnableSwagger2
public class SwaggerConfig {
	
	/**
     * Swagger API 문서 생성을 위한 주요 설정을 담당하는 Docket Bean을 생성한다.
     * <ul>
     *     <li>API를 스캔할 기본 패키지를 지정한다 (com.test.pulse.restcontroller).</li>
     *     <li>문서에 포함할 API 경로를 설정한다 (모든 경로).</li>
     *     <li>기본 응답 메시지 사용을 비활성화한다.</li>
     *     <li>API의 기본 정보(제목, 설명, 버전 등)를 설정한다.</li>
     *     <li>API 그룹을 정의하는 태그를 설정한다.</li>
     * </ul>
     *
     * @return Swagger 설정을 담은 Docket 인스턴스
     */
	@Bean
    public Docket api(){
		List<Predicate<RequestHandler>> predicateList = new ArrayList<>();
		//predicateList.add(RequestHandlerSelectors.basePackage("com.test.pulse.controller"));
		predicateList.add(RequestHandlerSelectors.basePackage("com.test.pulse.restcontroller"));
		
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.or(predicateList))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .tags(
                	new Tag("Course API","코스 등록 및 관리 관련 REST API"),
                  	new Tag("Board Suggestion API", "건의 게시판 REST API"),
                  	new Tag("Crew Board API", "크루 전용 게시판 REST API"),
                  	new Tag("Crew Chat API", "크루 채팅 REST API"),
                  	new Tag("Crew Board Comment API", "크루 전용 게시판 댓글 REST API"),
                  	new Tag("Crew Join API", "크루 가입 신청 REST API"),
                    new Tag("Crew API", "크루 메인 REST API"),
                    new Tag("Main Page API", "메인 페이지 REST API"),
                    new Tag("Workout API", "운동 기록 REST API")
                );
    }

    /**
     * API 문서에 표시될 기본 정보를 생성한다.
     * 제목, 설명, 버전 등의 정보를 포함한다.
     *
     * @return API 정보를 담은 ApiInfo 객체
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Pulse")
                .description("Spring Legacy Project - Pulse")
                //.termsOfServiceUrl("coding.toast.co.kr/api/")
                .version("1.0.0")
                .build();
    }

    /**
     * Swagger UI의 표시 방식을 설정하는 UiConfiguration Bean을 생성한다.
     * 여기서는 API 오퍼레이션(메소드) 목록을 알파벳 순으로 정렬하도록 설정한다.
     *
     * @return Swagger UI 설정을 담은 UiConfiguration 인스턴스
     */
    @Bean
    public UiConfiguration uiconfig() {
        return UiConfigurationBuilder
                .builder().operationsSorter(OperationsSorter.ALPHA)
                .build();
    }

}

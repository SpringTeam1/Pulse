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
 * Swagger Document 설정을 위한 클래스
 */
@EnableSwagger2
public class SwaggerConfig {
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

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Pulse")
                .description("Spring Legacy Project - Pulse")
                //.termsOfServiceUrl("coding.toast.co.kr/api/")
                .version("1.0.0")
                .build();
    }

    @Bean
    public UiConfiguration uiconfig() {
        return UiConfigurationBuilder
                .builder().operationsSorter(OperationsSorter.ALPHA)
                .build();
    }

}

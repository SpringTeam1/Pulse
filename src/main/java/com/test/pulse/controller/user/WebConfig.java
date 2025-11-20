package com.test.pulse.controller.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 관련 설정을 구성하는 클래스
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 정적 리소스 핸들러를 추가한다.
     * '/asset/pic/**' 경로를 외부 디렉토리와 매핑하여 프로필 이미지 등을 외부에서 접근할 수 있도록 한다.
     *
     * @param registry ResourceHandlerRegistry 객체
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
    	// 실제 프로젝트 경로에 맞게 수정 필요
        // 예: "file:///C:/projects/pulse/src/main/webapp/asset/pic/"
    	registry.addResourceHandler("/asset/pic/**")
        .addResourceLocations("file:/C:/your-project-root/webapp/asset/pic/");
    }
}
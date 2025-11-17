package com.test.pulse.controller.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
    	registry.addResourceHandler("/asset/pic/**")
        .addResourceLocations("file:/C:/your-project-root/webapp/asset/pic/");
    }
}

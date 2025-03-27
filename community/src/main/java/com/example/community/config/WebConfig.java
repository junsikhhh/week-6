package com.example.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 회원 프로필 이미지
        registry.addResourceHandler("/images/profile/**")
                .addResourceLocations("file:uploads/profile/");

        // 게시글 이미지
        registry.addResourceHandler("/images/post/**")
                .addResourceLocations("file:uploads/post/");
    }
}

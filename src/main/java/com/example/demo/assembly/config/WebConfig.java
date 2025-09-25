package com.example.demo.assembly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry reg) {
        reg.addMapping("/api/**")
                .allowedOrigins("*")   // 필요시 프론트 도메인으로 제한
                .allowedMethods("GET","OPTIONS")
                .allowedHeaders("*");
    }
}

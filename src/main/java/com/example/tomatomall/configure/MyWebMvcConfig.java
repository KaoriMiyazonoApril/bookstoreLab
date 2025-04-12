package com.example.tomatomall.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/accounts")
                .excludePathPatterns("/api/accounts/login")
                .excludePathPatterns("/images")
                .excludePathPatterns("/api/products")
                .excludePathPatterns("/api/products/*")
                .excludePathPatterns("/api/products/**")
                .order(1);
    }
}

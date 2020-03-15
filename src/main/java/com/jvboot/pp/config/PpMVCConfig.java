package com.jvboot.pp.config;

import com.jvboot.pp.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PpMVCConfig implements WebMvcConfigurer {
    private final LogInterceptor logs;

    @Autowired
    public PpMVCConfig(LogInterceptor logs) {
        this.logs = logs;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logs);
    }
}

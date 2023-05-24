package com.electronicstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import security.JwtAuthenticationEntryPoint;

@Component
public class MyAppConfig2 {
    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPint(){
        return new JwtAuthenticationEntryPoint();
    }
}

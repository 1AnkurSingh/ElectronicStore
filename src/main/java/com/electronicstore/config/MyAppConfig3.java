package com.electronicstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import security.JwtAuthenticationFilter;
@Component
public class MyAppConfig3 {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
}

package com.electronicstore.config.configclasses;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.electronicstore.security.JwtAuthenticationFilter;
@Component
public class MyAppConfig3 {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
}

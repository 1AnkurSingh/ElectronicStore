package com.electronicstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import security.JwtHelper;
@Component
public class AppConfig5 {
    @Bean
    public JwtHelper jwtHelper(){
        return new JwtHelper();
    }
}

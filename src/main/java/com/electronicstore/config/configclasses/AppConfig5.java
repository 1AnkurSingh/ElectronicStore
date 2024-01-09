package com.electronicstore.config.configclasses;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.electronicstore.security.JwtHelper;
@Component
public class AppConfig5 {
    @Bean
    public JwtHelper jwtHelper(){
        return new JwtHelper();
    }
}

package com.electronicstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails normalUser = User.builder()
                .username("Suraj")
                .password(passwordEncoder().encode("Suraj"))
                .roles("NORMAL")
                .build();


        UserDetails adminUser = User.builder()
                .username("Ankur")
                .password(passwordEncoder().encode("Ankur"))
                .roles("ADMIN").build();


//        InMemoryUserDetailsManager  is implementation class of UserDetailService
        return new InMemoryUserDetailsManager(normalUser,adminUser);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

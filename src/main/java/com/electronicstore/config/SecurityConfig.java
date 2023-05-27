package com.electronicstore.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import security.JwtAuthenticationEntryPoint;
import security.JwtAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

//    @Bean
//    public UserDetailsService userDetailsService(){
    //        UserDetails normalUser = User.builder()
    //                .username("Suraj")
    //                .password(passwordEncoder().encode("Suraj"))
    //                .roles("NORMAL")
    //                .build();
    //
    //
    //        UserDetails adminUser = User.builder()
    //                .username("Ankur")
    //                .password(passwordEncoder().encode("Ankur"))
    //                .roles("ADMIN").build();


//        InMemoryUserDetailsManager  is implementation class of UserDetailService
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                        .anyRequest()
//                                .authenticated()
//                                        .and().
//                formLogin()
//                .loginPage("login.html")
//                .loginProcessingUrl("/process-url")
//                .defaultSuccessUrl("/dashboard")
//                .failureUrl("error");
//
//
//        return http.build();

        http.csrf()
                        .disable()
                                .cors()
                                        .disable()
                                                .authorizeRequests()
                                                             .antMatchers("/auth/login")
                                                                         .permitAll()
                .antMatchers("/auth/google")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/users/create")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/delete/userId").hasRole("ADMIN")
                                                                                     .anyRequest()
                                                                                             .authenticated()
                                                                        .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
               return http.build();

    }



    @Bean
        DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public FilterRegistrationBean cordBean(){
       UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
//        configuration.setAllowedOrigins(Arrays.asList("https://domain2.com","http://localhost:4200"));
        configuration.addAllowedOriginPattern("*");// for All
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-type");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedMethod("Get");
        configuration.addAllowedMethod("Post");
        configuration.addAllowedMethod("Delete");
        configuration.addAllowedMethod("put");
        configuration.addAllowedMethod("Options");
        configuration.setMaxAge(3600L);




        source.registerCorsConfiguration("/**",configuration);


        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new CorsFilter());// source is needed new CorsFilter(source)
        filterRegistrationBean.setOrder(-1);
        return filterRegistrationBean;
    }

}

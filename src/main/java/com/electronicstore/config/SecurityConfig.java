package com.electronicstore.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.electronicstore.security.JwtAuthenticationEntryPoint;
import com.electronicstore.security.JwtAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    private final String [] PUBLIC_URLS={
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/v2/api-docs"

    };



//    loadUserByUsername Method:
//
//            "Called for user details during login."
//            "Needed for authenticating users."
//    UserDetailsService Interface:
//
//            "Has loadUserByUsername method."
//            "Can't directly call methods of an interface."
//    Using InMemoryUserDetailsManager:
//
//            "Implements UserDetailsService."
//            "Stores normalUser and adminUser details in memory."
//    Spring Security's Internal Working:
//
//            "Automatically uses loadUserByUsername."
//            "Fetches user details from InMemoryUserDetailsManager."
//            "Checks username and password for login.

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
                                .cors()// If we want to use cordBean then remove .cors().disable
                                        .disable()
                                                .authorizeRequests()
                                                             .antMatchers("/auth/login")
                                                                         .permitAll()
                .antMatchers(HttpMethod.GET,"/test")
                .permitAll()
                .antMatchers("/auth/google")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/users/create")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/delete/userId").hasRole("ADMIN")
                .antMatchers(PUBLIC_URLS)
                .permitAll()
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

    /*
        Role of Authentication Provider:

        DaoAuthenticationProvider handles the authentication process in Spring Security. It verifies the user's login credentials.
        Integration with UserDetailsService:

        It uses UserDetailsService. The loadUserByUsername method of the UserDetailsService interface retrieves user details, including username, password, and roles.
        Password Matching:

        Setting the password encoder with daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); allows it to encode the user-entered password and then compare it with the password stored in the database.
        Returning the Provider:

        Finally, it returns the fully configured DaoAuthenticationProvider bean, which is used in the Spring Security authentication mechanism.
        Main Usage:

        When a user attempts to log in, the DaoAuthenticationProvider verifies the provided username and password against the credentials stored in the database.
                Thus, DaoAuthenticationProvider is a crucial part of your Spring Security setup, managing user authentication efficiently and securely.*/
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
    // Use For cors with Spring boot
    public FilterRegistrationBean cordBean(){
       UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
//       configuration.setAllowedOrigins(Arrays.asList("https://domain2.com","http://localhost:4200"));
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
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

}

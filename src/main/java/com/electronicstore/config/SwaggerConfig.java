package com.electronicstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getAPiInfo());
        return docket;

    }

    private ApiInfo getAPiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Electronic Store Backend :APIS",
                "This is Backend project Created By Ankur Singh",
                "1.0.0v",
                "https://www.learncodewithdurgesh.com",
                new Contact("Ankur","https://instagram.com","ankurrana231002@gmail.com"),
                "License of APIS",
                "https://www.learncodewithdurgesh.com/about",
                new ArrayList<>()

        );

        return apiInfo;
    }


}

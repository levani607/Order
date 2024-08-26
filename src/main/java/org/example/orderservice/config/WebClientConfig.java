package org.example.orderservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebClientConfig {

    @Bean
    public RestTemplate restTemplate(){
     return    new RestTemplateBuilder()
                .additionalInterceptors((request,body,execution)->{
                    String tokenValue = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getTokenValue();
                    request.getHeaders().add("Authorization", "Bearer " + tokenValue);
                    return execution.execute(request, body);
                }).build();
    }

}

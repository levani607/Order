package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.exceptions.CustomException;
import org.example.orderservice.model.response.UserServiceUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final RestTemplate restTemplate;
    @Value("${user.user-uri}")
    private String userUri;

    @Cacheable(value = "userCache")
    public UserServiceUserResponse getUser(Long id){
        try {
            ResponseEntity<UserServiceUserResponse> exchange = restTemplate.exchange(
                    userUri + id,
                    HttpMethod.GET,
                    null,
                    UserServiceUserResponse.class
            );
            return exchange.getBody();
        }catch (HttpClientErrorException clientErrorException){
            throw new CustomException("User with id %s was not found!;".formatted(id), HttpStatus.NOT_FOUND);
        }
    }
}

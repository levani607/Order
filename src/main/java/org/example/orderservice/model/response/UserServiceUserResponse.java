package org.example.orderservice.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserServiceUserResponse implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String firstname;


}

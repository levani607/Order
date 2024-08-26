package org.example.orderservice.model.response;

import lombok.Data;
@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstname;

    public UserResponse(UserServiceUserResponse response) {
        this.id = response.getId();
        this.username = response.getUsername();
        this.email = response.getEmail();
        this.firstname = response.getFirstname();
    }
}

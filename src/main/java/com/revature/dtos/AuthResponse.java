package com.revature.annotations.dtos;

import com.revature.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login and Register respond with this
 */

@Data
@NoArgsConstructor
public class AuthResponse {
    private Integer id;
    private String email;
    private String role; // for conditional rendering in UI for admin
    private String firstName;
    private String lastName;

    public AuthResponse(User user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.role = user.getRole().getName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}

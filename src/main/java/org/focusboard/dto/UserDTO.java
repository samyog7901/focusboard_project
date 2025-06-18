package org.focusboard.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    // Getters and setters
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}


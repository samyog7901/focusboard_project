package org.focusboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserDTO {
    // Getters and setters
    private String username;
    private String email;
    private String password;
    @NotBlank
    private String confirmPassword;
}


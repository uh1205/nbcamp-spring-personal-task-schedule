package com.sparta.schedule.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    private String nickname;

    @Pattern(regexp = "^[a-z0-9]+$", message = "Username must contain only lowercase letters and numbers")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters long")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain only letters and numbers")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters long")
    private String password;

    private boolean admin = false;

    private String adminToken = ""; // AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC

}
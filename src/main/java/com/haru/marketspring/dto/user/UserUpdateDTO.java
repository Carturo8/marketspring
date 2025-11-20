package com.haru.marketspring.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        String username,

        @Email(message = "Email should be valid")
        String email,

        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
}

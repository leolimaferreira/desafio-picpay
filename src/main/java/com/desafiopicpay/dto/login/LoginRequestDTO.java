package com.desafiopicpay.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String senha
) {
}

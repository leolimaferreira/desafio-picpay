package com.desafiopicpay.dto.user;

import com.desafiopicpay.annotation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 150, message = "Name must be between 1 and 150 characters")
        String name,
        @NotBlank(message = "Document cannot be blank")
        @Size(min = 11, max = 14, message = "Document must be between a valid CPF or CNPJ")
        String document,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        @StrongPassword
        String password
) {
}

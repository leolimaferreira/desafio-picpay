package com.desafiopicpay.recovery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ChangePasswordDTO(
        @NotNull
        UUID tokenId,
        @NotBlank(message = "New password cannot be blank")
        @Size(min = 8, max = 20, message = "New password must be between 8 and 20 characters")
        String newPassword
) {
}

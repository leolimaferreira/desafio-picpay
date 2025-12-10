package com.desafiopicpay.dto.user;

import com.desafiopicpay.entities.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String document,
        String email,
        BigDecimal balance,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

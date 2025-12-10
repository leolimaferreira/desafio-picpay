package com.desafiopicpay.dto.transfer;

import com.desafiopicpay.dto.user.UserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferResponseDTO(
        UUID id,
        UserResponseDTO payer,
        UserResponseDTO payee,
        BigDecimal value,
        LocalDateTime createdAt
) {
}

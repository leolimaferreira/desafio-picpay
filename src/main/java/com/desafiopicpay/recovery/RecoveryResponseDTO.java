package com.desafiopicpay.recovery;

import com.desafiopicpay.dto.user.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record RecoveryResponseDTO(
        UUID id,
        LocalDateTime expirationDate,
        UserResponseDTO user
){
}

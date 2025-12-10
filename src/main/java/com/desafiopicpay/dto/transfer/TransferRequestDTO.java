package com.desafiopicpay.dto.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDTO(
        @NotNull(message = "Payer ID cannot be null")
        UUID payerId,
        @NotNull(message = "Payee ID cannot be null")
        UUID payeeId,
        @NotNull(message = "Transfer value cannot be null")
        @DecimalMin(value = "0.01", message = "Transfer value must be greater than zero")
        BigDecimal value
) {
}

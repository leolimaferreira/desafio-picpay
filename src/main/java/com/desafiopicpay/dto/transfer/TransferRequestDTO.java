package com.desafiopicpay.dto.transfer;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDTO(
        UUID payerId,
        UUID payeeId,
        BigDecimal value
) {
}

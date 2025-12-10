package com.desafiopicpay.dto.error;

public record FieldError(
        String field,
        String error
) {
}

package com.desafiopicpay.exception;

public class ExpiredRecoveryTokenException extends RuntimeException {
    public ExpiredRecoveryTokenException(String message) {
        super(message);
    }
}

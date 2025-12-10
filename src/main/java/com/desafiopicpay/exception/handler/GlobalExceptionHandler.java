package com.desafiopicpay.exception.handler;

import com.desafiopicpay.dto.error.ErrorResponse;
import com.desafiopicpay.exception.ConflictException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(ConflictException e, HttpServletRequest request) {
        return ErrorResponse.of(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                e.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<com.desafiopicpay.dto.error.FieldError> errorsList = fieldErrors.stream()
                .map(fe -> new com.desafiopicpay.dto.error.FieldError(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return ErrorResponse.of(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error",
                "One or more fields have an error",
                request.getRequestURI(),
                errorsList
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception e, HttpServletRequest request) {
        return ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An expected error occurred",
                request.getRequestURI()
        );
    }
}

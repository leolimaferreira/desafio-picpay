package com.desafiopicpay.controller.impl;

import com.desafiopicpay.dto.login.LoginRequestDTO;
import com.desafiopicpay.dto.login.LoginResponseDTO;
import com.desafiopicpay.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/authorization")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(authorizationService.login(dto));
    }
}

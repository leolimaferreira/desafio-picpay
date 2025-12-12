package com.desafiopicpay.controller.impl;

import com.desafiopicpay.dto.login.LoginRequestDTO;
import com.desafiopicpay.dto.login.LoginResponseDTO;
import com.desafiopicpay.recovery.ChangePasswordDTO;
import com.desafiopicpay.recovery.RecoveryRequestDTO;
import com.desafiopicpay.recovery.RecoveryResponseDTO;
import com.desafiopicpay.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/recovery-token")
    public ResponseEntity<RecoveryResponseDTO> generateRecoveryToken(
            @RequestBody @Valid RecoveryRequestDTO dto,
            @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(authorizationService.generateRecoveryToken(dto, token));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordDTO dto,
            @RequestHeader(name = "Authorization") String token
    ) {
        authorizationService.changePassword(dto, token);
        return ResponseEntity.noContent().build();
    }
}

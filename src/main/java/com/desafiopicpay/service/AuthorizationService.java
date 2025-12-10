package com.desafiopicpay.service;

import com.desafiopicpay.dto.login.LoginRequestDTO;
import com.desafiopicpay.dto.login.LoginResponseDTO;
import com.desafiopicpay.entities.User;
import com.desafiopicpay.exception.UnauthorizedException;
import com.desafiopicpay.repository.UserRepository;
import com.desafiopicpay.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {

    private static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("Login recebido com email: {}", dto.email());

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> {
                    log.warn(INVALID_EMAIL_OR_PASSWORD);
                    return new UnauthorizedException(INVALID_EMAIL_OR_PASSWORD);
                });

        log.info("Founded user: {}, role: {}", user.getName(), user.getRole());

        if (!passwordEncoder.matches(dto.senha(), user.getPassword())) {
            log.warn(INVALID_EMAIL_OR_PASSWORD);
            throw new BadCredentialsException("Invalid credentials");
        }

        log.info("Successfully validated password for user: {}", dto.email());

        String token = tokenService.generateToken(user);
        log.info("Successfully generated token for user: {}", dto.email());

        return new LoginResponseDTO(token, user.getName());
    }
}
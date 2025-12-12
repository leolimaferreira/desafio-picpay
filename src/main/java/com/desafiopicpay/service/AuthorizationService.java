package com.desafiopicpay.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.desafiopicpay.dto.login.LoginRequestDTO;
import com.desafiopicpay.dto.login.LoginResponseDTO;
import com.desafiopicpay.entities.PasswordRecoveryToken;
import com.desafiopicpay.entities.User;
import com.desafiopicpay.exception.ExpiredRecoveryTokenException;
import com.desafiopicpay.exception.NotFoundException;
import com.desafiopicpay.exception.SamePasswordException;
import com.desafiopicpay.exception.UnauthorizedException;
import com.desafiopicpay.mapper.PasswordRecoveryTokenMapper;
import com.desafiopicpay.recovery.ChangePasswordDTO;
import com.desafiopicpay.recovery.RecoveryRequestDTO;
import com.desafiopicpay.recovery.RecoveryResponseDTO;
import com.desafiopicpay.repository.PasswordRecoveryTokenRepository;
import com.desafiopicpay.repository.UserRepository;
import com.desafiopicpay.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {

    private static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final PasswordRecoveryTokenRepository passwordRecoveryTokenRepository;
    private final PasswordRecoveryTokenMapper passwordRecoveryTokenMapper;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("Login received with email: {}", dto.email());

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> {
                    log.warn(INVALID_EMAIL_OR_PASSWORD);
                    return new UnauthorizedException(INVALID_EMAIL_OR_PASSWORD);
                });

        log.info("Founded user: {}, role: {}", user.getName(), user.getRole());

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            log.warn(INVALID_EMAIL_OR_PASSWORD);
            throw new BadCredentialsException("Invalid credentials");
        }

        log.info("Successfully validated password for user: {}", dto.email());

        String token = tokenService.generateToken(user);
        log.info("Successfully generated token for user: {}", dto.email());

        return new LoginResponseDTO(token, user.getName());
    }

    public RecoveryResponseDTO generateRecoveryToken(RecoveryRequestDTO dto, String token) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new NotFoundException("User not found"));

        DecodedJWT decodedJWT = JWT.decode(token);

        if (!decodedJWT.getSubject().equals(user.getId().toString()) && !decodedJWT.getClaim("role").asString().equals("ADMIN")) {
            throw new UnauthorizedException("Only the user himself or an admin can request a recovery token");
        }

        PasswordRecoveryToken recoveryToken = new PasswordRecoveryToken();
        recoveryToken.setUser(user);
        PasswordRecoveryToken tokenSalvo = passwordRecoveryTokenRepository.save(recoveryToken);

        return passwordRecoveryTokenMapper.mapearParaRecuperacaoRespostaDTO(tokenSalvo);
    }

    public void changePassword(ChangePasswordDTO dto, String token) {
        PasswordRecoveryToken recoveryToken = passwordRecoveryTokenRepository.findById(dto.tokenId())
                .orElseThrow(() -> new NotFoundException("Recovery token not found"));

        DecodedJWT decodedJWT = JWT.decode(token);

        if (!decodedJWT.getSubject().equals(recoveryToken.getUser().getId().toString()) && !decodedJWT.getClaim("role").asString().equals("ADMIN")) {
            throw new UnauthorizedException("Only the user himself or an admin can change the password");
        }

        if (recoveryToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredRecoveryTokenException("Expired recovery token");
        }

        User user = userRepository.findById(recoveryToken.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
            throw new SamePasswordException("New password cannot be the same as the old password");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        recoveryToken.setUsed(true);
        passwordRecoveryTokenRepository.save(recoveryToken);
        userRepository.save(user);
    }
}
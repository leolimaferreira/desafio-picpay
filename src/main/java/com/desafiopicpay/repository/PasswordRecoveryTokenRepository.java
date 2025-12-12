package com.desafiopicpay.repository;

import com.desafiopicpay.entities.PasswordRecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordRecoveryToken, UUID> {
}

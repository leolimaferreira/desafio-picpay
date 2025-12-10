package com.desafiopicpay.mapper;

import com.desafiopicpay.entities.PasswordRecoveryToken;
import com.desafiopicpay.recovery.RecoveryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordRecoveryTokenMapper {

    private final UserMapper userMapper;

    public RecoveryResponseDTO mapearParaRecuperacaoRespostaDTO(PasswordRecoveryToken token) {
        return new RecoveryResponseDTO(
                token.getId(),
                token.getExpirationDate(),
                userMapper.mapToUserResponseDTO(token.getUser())
        );
    }
}

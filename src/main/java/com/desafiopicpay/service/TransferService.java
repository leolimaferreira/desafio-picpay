package com.desafiopicpay.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.desafiopicpay.dto.transfer.TransferRequestDTO;
import com.desafiopicpay.dto.transfer.TransferResponseDTO;
import com.desafiopicpay.entities.Transfer;
import com.desafiopicpay.entities.User;
import com.desafiopicpay.exception.InsufficientBalanceException;
import com.desafiopicpay.exception.NotFoundException;
import com.desafiopicpay.exception.UnauthorizedException;
import com.desafiopicpay.mapper.TransferMapper;
import com.desafiopicpay.repository.TransferRepository;
import com.desafiopicpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final UserRepository userRepository;
    private final ExternalAuthorizationService externalAuthorizationService;
    private final NotificationService notificationService;

    @Transactional
    public TransferResponseDTO createTransfer(TransferRequestDTO dto, String token) {
        User payer = userRepository.findById(dto.payerId())
                .orElseThrow(() -> new NotFoundException("Payer not found"));

        User payee = userRepository.findById(dto.payeeId())
                .orElseThrow(() -> new NotFoundException("Payee not found"));

        DecodedJWT decodedJWT = JWT.decode(token);

        boolean activeUserIsLojista = decodedJWT.getClaim("role").toString().equals("LOJISTA");

        if (payer.getRole().toString().equals("LOJISTA") || activeUserIsLojista) {
            throw new UnauthorizedException("Lojista users cannot initiate transfers");
        }

        if (payer.getBalance().compareTo(dto.value()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for the transfer");
        }

        if (!externalAuthorizationService.authorize()) {
            throw new UnauthorizedException("Transfer not authorized by external service");
        }

        Transfer transfer = transferMapper.mapToTransfer(payer, payee, dto.value());

        payer.setBalance(payer.getBalance().subtract(dto.value()));
        payee.setBalance(payee.getBalance().add(dto.value()));

        userRepository.saveAll(List.of(payer, payee));
        TransferResponseDTO response = transferMapper.mapToTransferResponseDTO(transferRepository.save(transfer));

        notificationService.sendNotification(
                payee.getId().toString(),
                String.format("You received a transfer with the amount R$ %.2f", dto.value())
        );

        return response;
    }
}

package com.desafiopicpay.service;

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

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final UserRepository userRepository;

    public TransferResponseDTO createTransfer(TransferRequestDTO dto) {
        User payer = userRepository.findById(dto.payerId())
                .orElseThrow(() -> new NotFoundException("Payer not found"));

        User payee = userRepository.findById(dto.payeeId())
                .orElseThrow(() -> new NotFoundException("Payee not found"));

        if (payer.getRole().toString().equals("LOJISTA")) {
            throw new UnauthorizedException("Lojista users cannot initiate transfers");
        }

        if (payer.getBalance().compareTo(dto.value()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for the transfer");
        }

        Transfer transfer = transferMapper.mapToTransfer(payer, payee, dto.value());

        return transferMapper.mapToTransferResponseDTO(transferRepository.save(transfer));
    }
}

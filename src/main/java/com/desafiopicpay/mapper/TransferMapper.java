package com.desafiopicpay.mapper;

import com.desafiopicpay.dto.transfer.TransferResponseDTO;
import com.desafiopicpay.entities.Transfer;
import com.desafiopicpay.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransferMapper {

    private final UserMapper userMapper;

    public Transfer mapToTransfer(User payer, User payee,  BigDecimal value) {
        Transfer transfer = new Transfer();
        transfer.setPayer(payer);
        transfer.setPayee(payee);
        transfer.setValue(value);
        return transfer;
    }

    public TransferResponseDTO mapToTransferResponseDTO(Transfer transfer) {
        return new TransferResponseDTO(
                transfer.getId(),
                userMapper.mapToUserResponseDTO(transfer.getPayer()),
                userMapper.mapToUserResponseDTO(transfer.getPayee()),
                transfer.getValue(),
                transfer.getCreatedAt()
        );
    }
}

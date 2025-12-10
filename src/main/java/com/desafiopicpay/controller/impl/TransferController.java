package com.desafiopicpay.controller.impl;

import com.desafiopicpay.controller.GenericController;
import com.desafiopicpay.dto.transfer.TransferRequestDTO;
import com.desafiopicpay.dto.transfer.TransferResponseDTO;
import com.desafiopicpay.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController implements GenericController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDTO> create(
            @RequestBody @Valid TransferRequestDTO dto,
            @RequestHeader(name = "Authorization") String token
    ) {
        TransferResponseDTO transfer = transferService.createTransfer(dto, token);
        URI location = generateHeaderLocation(transfer.id());
        return ResponseEntity.created(location).body(transfer);
    }
}

package com.desafiopicpay.controller.impl;

import com.desafiopicpay.controller.GenericController;
import com.desafiopicpay.dto.user.UserRequestDTO;
import com.desafiopicpay.dto.user.UserResponseDTO;
import com.desafiopicpay.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO user = userService.createUser(dto);
        URI location = generateHeaderLocation(user.id());
        return ResponseEntity.created(location).body(user);
    }
}

package com.desafiopicpay.service;

import com.desafiopicpay.dto.user.UserRequestDTO;
import com.desafiopicpay.dto.user.UserResponseDTO;
import com.desafiopicpay.entities.User;
import com.desafiopicpay.exception.ConflictException;
import com.desafiopicpay.mapper.UserMapper;
import com.desafiopicpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.existsByEmailOrDocument(dto.email(), dto.document())) {
            throw new ConflictException("There is already a user with this email or document.");
        }

        User user = userMapper.mapToUser(dto);

        return userMapper.mapToUserResponseDTO(userRepository.save(user));
    }
}

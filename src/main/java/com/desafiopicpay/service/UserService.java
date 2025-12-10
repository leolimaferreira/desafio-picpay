package com.desafiopicpay.service;

import com.desafiopicpay.dto.user.UserRequestDTO;
import com.desafiopicpay.dto.user.UserResponseDTO;
import com.desafiopicpay.entities.User;
import com.desafiopicpay.exception.ConflictException;
import com.desafiopicpay.mapper.UserMapper;
import com.desafiopicpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.existsByEmailOrDocument(dto.email(), dto.document())) {
            throw new ConflictException("There is already a user with this email or document.");
        }

        User user = userMapper.mapToUser(dto);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userMapper.mapToUserResponseDTO(userRepository.save(user));
    }
}

package com.desafiopicpay.mapper;

import com.desafiopicpay.dto.user.UserRequestDTO;
import com.desafiopicpay.dto.user.UserResponseDTO;
import com.desafiopicpay.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setDocument(dto.document());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public UserResponseDTO mapToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getDocument(),
                user.getEmail(),
                user.getBalance(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

package com.example.stripe.service.mapper;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.User;
import com.example.stripe.service.dto.UserDto;

@Service
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null)
            return null;
        return UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}

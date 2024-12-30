package com.example.trello.domain.user.dto;

import com.example.trello.global.entity.Role;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String name;
    private final String email;
    private final Role role;

    public UserResponseDto(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}

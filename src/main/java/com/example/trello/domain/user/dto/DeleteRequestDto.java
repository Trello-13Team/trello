package com.example.trello.domain.user.dto;

import lombok.Getter;

@Getter
public class DeleteRequestDto {
    public DeleteRequestDto(String password) {
        this.password = password;
    }

    private final String password;
}

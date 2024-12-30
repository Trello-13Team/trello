package com.example.trello.domain.user.dto;

import com.example.trello.domain.user.entity.User;
import com.example.trello.global.validation.ValidPassword;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String name;
    private String email;
    @ValidPassword
    private String password;
    private String role;

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public User toEntity() {
        return new User(
                this.name,
                this.email,
                this.password,
                this.role
        );
    }

}

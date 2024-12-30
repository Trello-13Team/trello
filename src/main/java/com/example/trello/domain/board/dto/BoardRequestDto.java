package com.example.trello.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {

    private final String title;
    private final String color;

    public BoardRequestDto(String title, String color) {
        this.title = title;
        this.color = color;
    }
}

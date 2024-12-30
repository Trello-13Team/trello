package com.example.trello.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {

    private final String title;
    private final String color;
    private final String imageUrl;

    public BoardRequestDto(String title, String color,String imageUrl) {
        this.title = title;
        this.color = color;
        this.imageUrl = imageUrl;
    }
}

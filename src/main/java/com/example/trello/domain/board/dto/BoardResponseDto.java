package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;

public class BoardResponseDto {

    private final Long boardId;
    private final String title;
    private final String color;
    private final String imageUrl;


    public BoardResponseDto(Long boardId, String title, String color, String imageUrl) {
        this.boardId = boardId;
        this.title = title;
        this.color = color;
        this.imageUrl = imageUrl;
    }

    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getColor(),
                board.getFileStorage().getFileUrl()
        );
    }
}

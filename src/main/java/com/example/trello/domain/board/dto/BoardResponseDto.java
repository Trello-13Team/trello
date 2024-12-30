package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private final Long boardId;
    private final String title;
    private final String color;


    public BoardResponseDto(Long boardId, String title, String color) {
        this.boardId = boardId;
        this.title = title;
        this.color = color;

    }

    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getColor()

        );
    }
}

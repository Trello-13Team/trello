package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.workspace.entity.Workspace;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindAllBoardResponseDto {

    private final Long boardId;
    private final String title;
    private final String color;
    private final String imageUrl;
    private final Workspace workspaceId;
    private final LocalDateTime createdAt;

    public FindAllBoardResponseDto( Workspace workspaceId,Long boardId, String title, String color, String imageUrl, LocalDateTime createdAt) {
        this.workspaceId = workspaceId;
        this.boardId = boardId;
        this.title = title;
        this.color = color;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public static FindAllBoardResponseDto toDto(Board board) {
        return new FindAllBoardResponseDto(
                board.getWorkspace(),
                board.getId(),
                board.getTitle(),
                board.getColor(),
                board.getImageUrl(),
                board.getCreatedAt()
        );
    }
}

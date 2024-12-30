package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;

import com.example.trello.domain.list.dto.ProcessListResponseDto;
import com.example.trello.domain.workspace.entity.Workspace;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FindBoardResponseDto {

    private final Long boardId;
    private final Workspace workspaceId;
    private final String title;
    private final String color;
    private final String imageUrl;
    private final List<CardResponseDto> cards;
    private final List<ProcessListResponseDto> lists;
    private final LocalDateTime createdAt;

    public FindBoardResponseDto(Long boardId, Workspace workspaceId, String title, String color, String imageUrl, List<CardResponseDto> cards, List<ProcessListResponseDto> lists, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.workspaceId = workspaceId;
        this.title = title;
        this.color = color;
        this.imageUrl = imageUrl;
        this.cards = cards;
        this.lists = lists;
        this.createdAt = createdAt;
    }

    public static FindBoardResponseDto toDto(Board board) {
        return new FindBoardResponseDto(
                board.getId(),
                board.getWorkspace(),
                board.getTitle(),
                board.getColor(),
                board.getFileStorage().getFileUrl(),
                board.getCards().stream()
                        .map(CardResponseDto::toDto)
                        .collect(Collectors.toList()),
                board.getLists().stream()
                        .map(ProcessListResponseDto::toDto)
                        .collect(Collectors.toList()),
                board.getCreatedAt()
        );
    }
}

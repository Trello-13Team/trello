package com.example.trello.domain.list.dto;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.list.entity.ProcessList;
import lombok.Getter;

@Getter
public class ProcessListResponseDto {
    private final Long id;
    private final Board boardId;
    private final String title;
    private final Integer order;

    public ProcessListResponseDto(Long id, Board boardId, String title, Integer order) {
        this.id = id;
        this.boardId = boardId;
        this.title = title;
        this.order = order;
    }

    public static ProcessListResponseDto toDto(ProcessList processList) {
        return new ProcessListResponseDto(
                processList.getId(),
                processList.getBoard(),
                processList.getTitle(),
                processList.getOrder()
        );
    }
}
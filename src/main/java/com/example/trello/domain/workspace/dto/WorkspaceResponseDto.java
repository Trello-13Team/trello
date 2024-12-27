package com.example.trello.domain.workspace.dto;

import lombok.Getter;

@Getter
public class WorkspaceResponseDto {
    private final String name;
    private final String description;

    public WorkspaceResponseDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

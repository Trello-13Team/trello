package com.example.trello.domain.workspace.dto;

import com.example.trello.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class WorkspaceResponseDto {
    private final Long id;
    private final String name;
    private final String description;

    public WorkspaceResponseDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static WorkspaceResponseDto toDto(Workspace workspace) {
        return new WorkspaceResponseDto(workspace.getId(), workspace.getName(), workspace.getDescription());
    }
}

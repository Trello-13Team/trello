package com.example.trello.domain.workspace.dto;

import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class WorkspaceRequestBody {
    private String name;
    private String description;

    public Workspace toEntity(User user) {
        return new Workspace(
                this.name,
                this.description,
                user
        );
    }


}

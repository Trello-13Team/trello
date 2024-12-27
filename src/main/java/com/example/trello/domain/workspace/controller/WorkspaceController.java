package com.example.trello.domain.workspace.controller;

import com.example.trello.domain.workspace.dto.WorkspaceRequestBody;
import com.example.trello.domain.workspace.dto.WorkspaceResponseDto;
import com.example.trello.domain.workspace.service.WorkspaceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceRequestBody requestBody) {
        return workspaceService.createWorkspace(requestBody);
    }


//    public ResponseEntity<WorkspaceResponseDto>

}

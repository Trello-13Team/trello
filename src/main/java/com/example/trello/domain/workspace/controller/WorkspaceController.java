package com.example.trello.domain.workspace.controller;

import com.example.trello.domain.workspace.dto.WorkspaceRequestBody;
import com.example.trello.domain.workspace.dto.WorkspaceResponseDto;
import com.example.trello.domain.workspace.service.WorkspaceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    //워크스페이스 생성
    //requestbody : name, description
    @PostMapping("/admin")
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceRequestBody requestBody) {
        return workspaceService.createWorkspace(requestBody);
    }

    //워크스페이스 조회
    @GetMapping
    public ResponseEntity<List<WorkspaceResponseDto>> findWorkspaceByUser() {
        return workspaceService.findWorkspaceByUser();
    }


    //워크스페이스 수정
    //requestbody : name, description
    @PatchMapping("/{id}")
    public ResponseEntity<WorkspaceResponseDto> patchWorkspace(@PathVariable Long id, @RequestBody WorkspaceRequestBody requestBody) {
        return workspaceService.patchWorkspace(id, requestBody);
    }

    //워크스페이스 삭제
    @DeleteMapping("/{id}")
    public void deleteWorkspace(@PathVariable Long id){
        workspaceService.delete(id);
    }

}

package com.example.trello.domain.workspace.service;

import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.dto.WorkspaceRequestBody;
import com.example.trello.domain.workspace.dto.WorkspaceResponseDto;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class WorkspaceService {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(UserRepository userRepository, WorkspaceRepository workspaceRepository) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public ResponseEntity<WorkspaceResponseDto> createWorkspace(WorkspaceRequestBody requestBody) {
        User user = userRepository.findById(requestBody.getUserId()).orElseThrow();
//        if(!user.getRole().equals(Role.ADMIN)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin 권한이 아닌 유저는 워크스페이스를 생성할 수 없습니다.");
//        }

        workspaceRepository.save(requestBody.toEntity(user));
        WorkspaceResponseDto workspaceResponseDto = new WorkspaceResponseDto(requestBody.getName(), requestBody.getDescription());

        return new ResponseEntity<>(workspaceResponseDto, HttpStatus.CREATED);
    }


}

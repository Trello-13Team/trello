package com.example.trello.domain.workspace.repository;

import com.example.trello.domain.workspace.dto.WorkspaceResponseDto;
import com.example.trello.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Workspace findByUserId(Long id);

}

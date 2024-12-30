package com.example.trello.domain.list.controller;

import com.example.trello.domain.list.dto.ProcessListRequestDto;
import com.example.trello.domain.list.dto.ProcessListResponseDto;
import com.example.trello.domain.list.dto.UpdateProcessListRequestDto;
import com.example.trello.domain.list.service.ProcessListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProcessListController {

    private final ProcessListService processListService;

    @PostMapping("/lists/{boardId}")
    public ResponseEntity<ProcessListResponseDto> createList(
            @PathVariable(name = "boardId") Long boardId,
            @RequestBody ProcessListRequestDto listRequestDto,
            HttpServletRequest request
    ) {

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        ProcessListResponseDto createList = processListService.createList(boardId, listRequestDto, userId);

        return new ResponseEntity<>(createList, HttpStatus.CREATED);
    }

    @DeleteMapping("/lists/{listId}")
    public ResponseEntity<Void> deleteList(
            @PathVariable Long listId,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        processListService.deleteList(listId, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/lists/{boardId}/{listId}")
    public ResponseEntity<ProcessListResponseDto> updateBoard(
            @PathVariable Long boardId,
            @PathVariable Long listId,
            @RequestBody UpdateProcessListRequestDto updateProcessListRequestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        ProcessListResponseDto updateList = processListService.updateList(boardId, listId, updateProcessListRequestDto, userId);

        return new ResponseEntity<>(updateList, HttpStatus.OK);
    }
}

package com.example.trello.domain.board.controller;


import com.example.trello.domain.board.dto.BoardRequestDto;
import com.example.trello.domain.board.dto.BoardResponseDto;
import com.example.trello.domain.board.dto.FindAllBoardResponseDto;
import com.example.trello.domain.board.dto.FindBoardResponseDto;
import com.example.trello.domain.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards/{workspaceId}")
    public ResponseEntity<BoardResponseDto> createBoard(
            @PathVariable(name = "workspaceId") Long workspaceId,
            @RequestBody BoardRequestDto boardRequestDto,
            HttpServletRequest request
    ) {

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        BoardResponseDto createBoard = boardService.createBoard(workspaceId, boardRequestDto, userId);

        return new ResponseEntity<>(createBoard, HttpStatus.CREATED);
    }

    @PatchMapping("/boards/{boardId}/")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @PathVariable(name = "boardId") Long boardId,
            @RequestBody BoardRequestDto boardRequestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        BoardResponseDto updateBoard = boardService.updateBoard(userId, boardId, boardRequestDto);

        return new ResponseEntity<>(updateBoard, HttpStatus.OK);
    }

    // 보드 다건 조회
    @GetMapping("/boards/{workspaceId}")
    public ResponseEntity<Page<FindAllBoardResponseDto>> findAllBoard(
            @PageableDefault(page = 1)
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable(name = "workspaceId") Long workspaceId,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        Page<FindAllBoardResponseDto> findAllBoard = boardService.findAllBoard(
                pageable,workspaceId, userId
        );

        return new ResponseEntity<>(findAllBoard, HttpStatus.OK);
    }
    // 보드 단건 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<FindBoardResponseDto> findBoardById(
            @PathVariable Long boardId,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        FindBoardResponseDto findBoard = boardService.FindBoardById(boardId, userId);

        return new ResponseEntity<>(findBoard, HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}/")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {

        boardService.deleteBoard(boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}

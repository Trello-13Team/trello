package com.example.trello.domain.board.service;

import com.example.trello.domain.board.dto.BoardRequestDto;
import com.example.trello.domain.board.dto.BoardResponseDto;
import com.example.trello.domain.board.dto.FindAllBoardResponseDto;
import com.example.trello.domain.board.dto.FindBoardResponseDto;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;

    public Workspace findbyIdWorkspace(Long id) {
        return workspaceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NO_CONTENT, "해당 워크스페이스가 존재하지 않습니다."));
    }
    public BoardResponseDto createBoard(Long workspaceId, BoardRequestDto boardRequestDto, Long userId) {

        Workspace findworksapce = findbyIdWorkspace(workspaceId);

        Board board = new Board(findworksapce,boardRequestDto.getTitle(), boardRequestDto.getColor());

        Board savedBoard = boardRepository.save(board);

        return BoardResponseDto.toDto(savedBoard);
    }

    public BoardResponseDto updateBoard(Long userId, Long boardId, BoardRequestDto boardRequestDto) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        findBoard.updateBoard(boardRequestDto);

        Board savedBoard = boardRepository.save(findBoard);

        return BoardResponseDto.toDto(savedBoard);
    }

    public void deleteBoard(Long boardId){

        Board deleteBoard = boardRepository.findByIdOrElseThrow(boardId);

        boardRepository.delete(deleteBoard);
    }

    @Transactional
    public Page<FindAllBoardResponseDto> findAllBoard(Pageable pageable, Long workspaceId, Long userId) {

        Workspace findWorkspace = findbyIdWorkspace(workspaceId);
        Page<Board> boardsPages;

        boardsPages = boardRepository.findAll(pageable);
        return boardsPages.map(FindAllBoardResponseDto::toDto);
    }

    public FindBoardResponseDto FindBoardById(Long boardId, Long userId) {
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        return FindBoardResponseDto.toDto(findBoard);
    }
}

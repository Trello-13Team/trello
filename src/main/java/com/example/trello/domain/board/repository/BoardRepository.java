package com.example.trello.domain.board.repository;

import com.example.trello.domain.board.entity.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllBoards(Pageable pageable);

    default Board findByIdOrElseThrow(Long boardId) {
        return findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found")
        );
    }


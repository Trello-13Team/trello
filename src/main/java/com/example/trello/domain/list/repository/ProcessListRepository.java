package com.example.trello.domain.list.repository;

import com.example.trello.domain.list.entity.ProcessList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface ProcessListRepository extends JpaRepository<ProcessList, Long> {

    default ProcessList findByIdOrElseThrow(Long processId) {
        return findById(processId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "list not found")
        );
    }
    @Query("select MAX (l.order) from ProcessList l where l.board.id = :boardId")
    Integer findLastOrder(Long boardId);

    List<ProcessList> findListByBoardId(Long boardId);

}

package com.example.trello.domain.list.repository;

import com.example.trello.domain.list.entity.ProcessList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessListRepository extends JpaRepository<ProcessList, Long> {
}

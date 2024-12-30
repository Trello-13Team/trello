package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.FileMetaDataDto;
import com.example.trello.global.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileStorage, Long> {
}

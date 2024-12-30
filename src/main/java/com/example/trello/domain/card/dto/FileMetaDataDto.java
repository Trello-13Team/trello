package com.example.trello.domain.card.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FileMetaDataDto {
    private Long id;
    private String fileName;
    private String fileKey;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    @QueryProjection
    public FileMetaDataDto(Long id, String fileName, String fileKey, Long fileSize, LocalDateTime uploadedAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
    }
}

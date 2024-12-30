package com.example.trello.domain.card.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FileMetaDataDto {
    private Long id;
    private String fileUrl;
    private String fileKey;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    @QueryProjection
    public FileMetaDataDto(Long id, String fileUrl, String fileKey, Long fileSize, LocalDateTime uploadedAt) {
        this.id = id;
        this.fileUrl = fileUrl;
        this.fileKey = fileKey;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
    }
}

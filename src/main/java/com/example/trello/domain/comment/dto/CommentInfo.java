package com.example.trello.domain.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentInfo {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    @QueryProjection
    public CommentInfo(Long id, String content, String author, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }
}

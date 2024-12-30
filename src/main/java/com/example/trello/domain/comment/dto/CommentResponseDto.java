package com.example.trello.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final Long authorId;
    private final String author;
    private final LocalDateTime createdAt;
}

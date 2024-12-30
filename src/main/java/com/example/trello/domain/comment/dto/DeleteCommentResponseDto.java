package com.example.trello.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteCommentResponseDto {
    Long id;

    public DeleteCommentResponseDto(Long id) {
        this.id = id;
    }
}

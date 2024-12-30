package com.example.trello.domain.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    @Length(min = 1, max = 200)
    @NotEmpty
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}

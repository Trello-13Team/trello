package com.example.trello.domain.card.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CreateCardResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime dueDate;
}

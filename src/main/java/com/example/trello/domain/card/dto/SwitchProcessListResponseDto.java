package com.example.trello.domain.card.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class SwitchProcessListResponseDto {
    private final Long processListId;
}

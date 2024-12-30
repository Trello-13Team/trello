package com.example.trello.domain.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class FindCardListRequestDto {
    private String title;
    private String content;
    private LocalDateTime dueDate;
    private String userName;
    private Long pageNumber;
    private Long pageSize;

    public FindCardListRequestDto(String title, String content, LocalDateTime dueDate, String userName, Long pageNumber, Long pageSize) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.userName = userName;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}

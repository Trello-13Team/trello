package com.example.trello.domain.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class FindCardListRequestDto {
    @Length(max = 20)
    private String title;
    @Length(max = 20)
    private String content;

    private LocalDateTime dueDate;
    @Length(max = 20)
    private String userName;
    @Length(min = 1, max = 20)
    private Long pageNumber;
    @Length(min = 5, max = 20)
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

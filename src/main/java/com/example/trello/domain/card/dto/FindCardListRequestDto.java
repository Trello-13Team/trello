package com.example.trello.domain.card.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Nullable
    private Long boardId;

    private LocalDateTime dueDate;
    @Length(max = 20)
    private String userName;
    @Min(1)
    private Long pageNumber;
    @Min(5)@Max(10)
    private Long pageSize;

    public FindCardListRequestDto(String title, String content, Long boardId ,LocalDateTime dueDate, String userName, Long pageNumber, Long pageSize) {
        this.title = title;
        this.content = content;
        this.boardId = boardId;
        this.dueDate = dueDate;
        this.userName = userName;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}

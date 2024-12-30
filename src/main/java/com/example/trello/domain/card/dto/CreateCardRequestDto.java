package com.example.trello.domain.card.dto;

import com.example.trello.global.validation.ValidDueDate;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateCardRequestDto {
    @NotEmpty
    @Length(min = 1, max = 100)
    private final String title;
    @NotEmpty
    @Length(min = 1, max = 500)
    private final String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ValidDueDate
    private final LocalDateTime dueDate;

}

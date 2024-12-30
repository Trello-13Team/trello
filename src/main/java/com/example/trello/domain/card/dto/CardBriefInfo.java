package com.example.trello.domain.card.dto;

import com.example.trello.domain.member.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CardBriefInfo {
    private  Long id;
    private  String title;
    private  LocalDateTime dueDate;
    private  Long userId;
    private  String userName;
    private  Long commentsCount;

    @QueryProjection
    public CardBriefInfo(Long id, String title, LocalDateTime dueDate, Long userId, String userName, Long commentsCount) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.userId = userId;
        this.userName = userName;
        this.commentsCount = commentsCount;
    }
}

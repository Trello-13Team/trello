package com.example.trello.domain.card.dto;

import com.example.trello.domain.comment.dto.CommentInfo;
import com.example.trello.domain.member.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CardDetailedInfo {
    private  Long cardId;
    private  String title;
    private  String content;
    private  LocalDateTime dueDate;
    private  Long userId;
    private  String userName;
    private  List<CommentInfo> comments;
    @QueryProjection
    public CardDetailedInfo( Long cardId, String title, String content, LocalDateTime dueDate, Long userId, String userName) {
        this.title = title;
        this.cardId = cardId;
        this.content = content;
        this.dueDate = dueDate;
        this.userId = userId;
        this.userName = userName;
    }


}

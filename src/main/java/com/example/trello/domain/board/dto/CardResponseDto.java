package com.example.trello.domain.board.dto;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.user.entity.User;
import lombok.Getter;

@Getter
public class CardResponseDto {

    private final Long id;
    private final Long userId;
    private final Long listId;
    private final String title;
    private final String content;

    public CardResponseDto(Long id, Long userId, Long listId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.listId = listId;
        this.title = title;
        this.content = content;
    }

    public static CardResponseDto toDto(Card card) {
        return new CardResponseDto(
                card.getId(),
                card.getUser().getId(),
                card.getProcessList().getId(),
                card.getTitle(),
                card.getContent()
        );
    }
}
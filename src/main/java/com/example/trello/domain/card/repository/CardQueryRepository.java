package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.CardBriefInfo;
import com.example.trello.domain.card.dto.CardDetailedInfo;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CardQueryRepository {

    List<CardBriefInfo> searchAllCards(String title, String content, LocalDateTime dueDate, String responsibleUserName, Long boardId, Long workspaceId,
                                       Long pageNumber, Long pageSize);

    CardDetailedInfo findCardDetailedInfoById(Long id);
}

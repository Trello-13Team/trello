package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.entity.QCard;
import com.querydsl.core.QueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardQueryRepositoryimpl implements CardQueryRepository {
    private final QueryFactory queryFactory;
    private final QCard card;
}

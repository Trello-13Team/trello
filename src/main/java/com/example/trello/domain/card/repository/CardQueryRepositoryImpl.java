package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.CardBriefInfo;
import com.example.trello.domain.card.dto.CardDetailedInfo;
import com.example.trello.domain.card.dto.QCardBriefInfo;
import com.example.trello.domain.card.dto.QCardDetailedInfo;
import com.example.trello.domain.card.entity.QCard;
import com.example.trello.domain.comment.dto.CommentInfo;
import com.example.trello.domain.comment.dto.QCommentInfo;
import com.example.trello.domain.comment.entity.QComment;
import com.example.trello.domain.member.entity.QMember;
import com.example.trello.domain.user.entity.QUser;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CardQueryRepositoryImpl implements CardQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final QCard card = QCard.card;
    private final QUser user = QUser.user;
    private final QMember member = QMember.member;
    private final QComment comment = QComment.comment;

    @Override
    public List<CardBriefInfo> searchAllCards(String title, String content, LocalDateTime dueDate, String responsibleUserName,
                                              Long boardId, Long workspaceId,Long pageNumber, Long pageSize) {
        return queryFactory.select(new QCardBriefInfo(card.id, card.title, card.dueDate, card.user.id, card.user.name, comment.count()))
                .from(card)
                .innerJoin(card.user, user)
                .innerJoin(card.comments, comment)
                .where(titleContains(title), contentContains(content), dueDateEq(dueDate), responsibleUserEq(responsibleUserName))
                .where(card.boardId.eq(boardId), card.workspaceId.eq(workspaceId))
                .offset(pageNumber)
                .limit(pageSize)
                .orderBy(card.dueDate.asc())
                .fetch();

    }
    @Override
    public CardDetailedInfo findCardDetailedInfoById(Long id) {



        List<CommentInfo> commentInfoList = queryFactory.select(new QCommentInfo(comment.id,comment.content,comment.user.name,comment.createdAt)).from(comment)
                .where(comment.card.id.eq(id))
                .fetch();
        CardDetailedInfo cardDetailedInfo = queryFactory.select(new QCardDetailedInfo(
                        card.id,
                        card.title,
                        card.content,
                        card.dueDate,
                        user.id,
                        user.name
                ))
                .from(card)
                .leftJoin(card.user, user)
                .fetchOne();
        if(cardDetailedInfo != null) {
            cardDetailedInfo.setComments(commentInfoList);
        }
        return cardDetailedInfo;

    }

    // contains를 통해 %title% 연산 -> 앞뒤로 string이 더 존재해도 검색가능
    private BooleanExpression titleContains(String title) {
        if (title == null) return null;
        return card.title.contains(title);
    };

    private BooleanExpression contentContains(String title) {
        if (title == null) return null;
        return card.title.contains(title);
    };

    private BooleanExpression dueDateEq(LocalDateTime dueDate) {
        if (dueDate == null) return null;
        return card.dueDate.eq(dueDate);
    }

    private BooleanExpression responsibleUserEq(String responsibleUserName) {
        if (responsibleUserName == null) return null;
        return user.name.eq(responsibleUserName);
    }
}

package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.CardBriefInfo;
import com.example.trello.domain.card.dto.CardDetailedInfo;
import com.example.trello.domain.card.dto.FileMetaDataDto;
import com.example.trello.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface CardRepository extends JpaRepository<Card, Long>, CardQueryRepository {
    List<CardBriefInfo> searchAllCards(String title, String content, LocalDateTime dueDate, String responsibleUserName, Long boardId, Long workspaceId,
                                       Long pageNumber, Long pageSize);
    CardDetailedInfo findCardDetailedInfoById(Long id);
    List<FileMetaDataDto> findMetaDataDtoByCardId(Long cardId);
}

package com.example.trello.domain.card.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class FindCardListResponseDto {
    private final List<CardBriefInfo> cards;
    private final pageInfo pageInfo;

    @EqualsAndHashCode
    @Getter
    public static class pageInfo{
        private final Long totalElements;
        private final Long pageNumber;
        private final Long pageSize;

        public pageInfo(Long totalElements, Long pageNumber, Long pageSize) {
            this.totalElements = totalElements;
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }
    }
}

package com.example.trello.domain.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindFileResponseDto {
    private List<FileMetaDataDto> files;

    public FindFileResponseDto(List<FileMetaDataDto> files) {
        this.files = files;
    }
}

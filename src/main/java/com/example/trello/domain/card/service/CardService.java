package com.example.trello.domain.card.service;

import com.example.trello.domain.card.dto.CreateCardRequestDto;
import com.example.trello.domain.card.dto.CreateCardResponseDto;
import com.example.trello.domain.card.dto.SwitchProcessListResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.list.repository.ProcessListRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.exception.BaseException;
import com.example.trello.global.exception.UnauthorizedException;
import com.example.trello.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final ProcessListRepository processListRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCardResponseDto createCard(CreateCardRequestDto requestDto,Long userId ,Long workspaceId, Long processListId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_USER)
        );
        ProcessList processList = processListRepository.findById(processListId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_PROCESSLIST)
        );
        checkWriteRole(workspaceId, userId);
        Card card = Card.builder()
                .title(requestDto.getTitle())
                .user(user)
                .content(requestDto.getContent())
                .dueDate(requestDto.getDueDate())
                .processList(processList)
                .build();
        cardRepository.save(card);
        return new CreateCardResponseDto(card.getId(),card.getTitle(),card.getContent(),card.getDueDate());
    }

    @Transactional
    public SwitchProcessListResponseDto switchProcessList(Long cardId, Long processListId,Long workspaceId ,Long userId) {
        ProcessList processList = processListRepository.findById(processListId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_PROCESSLIST)
        );
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_CARD)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_USER)
        );
        checkWriteRole(workspaceId, userId);
        card.switchProcessList(processList);
        return new SwitchProcessListResponseDto(processListId);
    }

    private void checkWriteRole(Long workspaceId, Long userId) {
        Member member = memberRepository.findByUser_IdAndWorkspace_Id(userId, workspaceId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_MEMBER)
        );
        if(member.getRole() == Member.MemberRole.MANAGER ){
            throw new BaseException(ErrorCode.NOT_ALLOW_MANAGER);
        }
    }


}

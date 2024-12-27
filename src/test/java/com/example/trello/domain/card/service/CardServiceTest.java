package com.example.trello.domain.card.service;

import com.example.trello.domain.card.dto.*;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.list.repository.ProcessListRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.entity.Workspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    CardRepository cardRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ProcessListRepository processListRepository;
    @InjectMocks
    CardService cardService;

    User user;
    Member member;
    ProcessList processList;
    Workspace workspace;
    Card card;
    @BeforeEach
    void setUp() {
        Long cardId = 1L;
        Long memberId = 1L;
        Long processListId = 1L;
        Long userId = 1L;
        Long workspaceId = 1L;
        String title = "title";
        String content = "content";
        LocalDateTime dueDate = LocalDateTime.of(2025,1,1, 0, 0, 0);

        user = User.builder().build();
        workspace = Workspace.builder().build();
        processList = ProcessList.builder().build();
        member = Member.builder().build();

        user = Mockito.spy(user);
        member = Mockito.spy(member);
        processList = Mockito.spy(processList);
        workspace = Mockito.spy(workspace);

        card = Card.builder().title(title).content(content).dueDate(dueDate).user(user)
                .processList(processList).build();


        when(memberRepository.findByUser_IdAndWorkspace_Id(user.getId(),workspace.getId())).thenReturn(Optional.ofNullable(member));




    }

    @Test
    void createCardSuccess() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(processListRepository.findById(processList.getId())).thenReturn(Optional.ofNullable(processList));
        CreateCardRequestDto requestDto = new CreateCardRequestDto(card.getTitle(), card.getContent(), card.getDueDate());
        CreateCardResponseDto responseDto = new CreateCardResponseDto(card.getId(), card.getTitle(),card.getContent(),card.getDueDate());

        assertThat(responseDto.equals(cardService.createCard(requestDto,user.getId(),workspace.getId(),processList.getId()))).isTrue();
    }

    @Test
    void switchProcessListSuccess() {
        SwitchProcessListResponseDto responseDto = new SwitchProcessListResponseDto(processList.getId());
        when(processListRepository.findById(processList.getId())).thenReturn(Optional.ofNullable(processList));
        card = Card.builder().processList(processList).build();
        ProcessList formerProcessList = ProcessList.builder().build();
        formerProcessList.getCards().add(card);

        when(cardRepository.findById(card.getId())).thenReturn(Optional.of(card));

        assertThat(responseDto.equals(cardService.switchProcessList(card.getId(),processList.getId(),workspace.getId(),user.getId()))).isTrue();
    }

    @Test
    void updateCardSuccess() {
        UpdateCardRequestDto requestDto = new UpdateCardRequestDto(card.getTitle(), card.getContent(), card.getDueDate());
        UpdateCardResponseDto responseDto = new UpdateCardResponseDto(card.getTitle(),card.getContent(),card.getDueDate());
        when(cardRepository.findById(card.getId())).thenReturn(Optional.of(card));
        assertThat(responseDto.equals(cardService.updateCard(requestDto,user.getId(),workspace.getId(), card.getId()))).isTrue();
    }


}
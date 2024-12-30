package com.example.trello.domain.card.service;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.*;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.card.repository.FileRepository;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.list.repository.ProcessListRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.S3.S3Service;
import com.example.trello.global.S3.S3Uploader;
import com.example.trello.global.dto.UploadFileInfo;
import com.example.trello.global.entity.FileStorage;
import com.example.trello.global.exception.BaseException;
import com.example.trello.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final ProcessListRepository processListRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final S3Service s3Service;
    private final FileRepository fileRepository;

    @Transactional
    public CreateCardResponseDto createCard(CreateCardRequestDto requestDto,Long userId ,Long workspaceId, Long boardId,Long processListId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_USER)
        );
        ProcessList processList = processListRepository.findById(processListId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_PROCESSLIST)
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_BOARD)
        );
        checkWriteRole(workspaceId, userId);
        Card card = Card.builder()
                .title(requestDto.getTitle())
                .user(user)
                .content(requestDto.getContent())
                .dueDate(requestDto.getDueDate())
                .board(board)
                .workspaceId(workspaceId)
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

        checkWriteRole(workspaceId, userId);
        card.switchProcessList(processList);
        return new SwitchProcessListResponseDto(processListId);
    }

    @Transactional
    public UpdateCardResponseDto updateCard(UpdateCardRequestDto requestDto,Long userId, Long workspace, Long cardId) {
        checkWriteRole(workspace,userId);
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_CARD)
        );
        card.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getDueDate());
        return new UpdateCardResponseDto(card.getTitle(),card.getContent(),card.getDueDate());
    }

    @Transactional
    public DeleteCardResponseDto deleteCard(Long userId, Long workspace, Long cardId) {
        checkWriteRole(workspace,userId);
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_CARD)
                        );
        cardRepository.delete(card);
        return new DeleteCardResponseDto(cardId);
    }

    @Transactional
    public FindCardListResponseDto findCardList(Long userId, FindCardListRequestDto requestBody, Long workspaceId) {
        checkReadRole(workspaceId, userId);
        List<CardBriefInfo> cardBriefInfoList = cardRepository.searchAllCards(requestBody.getTitle(), requestBody.getContent()
                , requestBody.getDueDate(),requestBody.getUserName(),requestBody.getBoardId(),workspaceId
                , requestBody.getPageNumber()-1, requestBody.getPageSize());

        return new FindCardListResponseDto(cardBriefInfoList,new FindCardListResponseDto.pageInfo((long)cardBriefInfoList.size(),
                requestBody.getPageNumber(), requestBody.getPageSize()));
    }

    @Transactional
    public CardDetailedInfo findCardDetailedInfo(Long userId, Long workspace, Long cardId) {
        checkReadRole(workspace,userId);

        return cardRepository.findCardDetailedInfoById(cardId);
    }

    @Transactional
    public UploadFileInfo uploadFile(Long userId, Long workspaceId, Long cardId, MultipartFile file) {
        checkWriteRole(workspaceId,userId);
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_CARD)
        );
        UploadFileInfo fileInfo = s3Service.uploadFile(file);
        FileStorage fileStorage = FileStorage.builder().fileKey(fileInfo.fileKey()).fileUrl(fileInfo.fileUrl()).fileSize(file.getSize()).card(card).build();
        fileRepository.save(fileStorage);
        return new UploadFileInfo(fileStorage.getFileUrl(), fileStorage.getFileKey());
    }

    @Transactional
    public FindFileResponseDto getFileMetaData(Long userId, Long workspace, Long cardId) {
        checkReadRole(workspace,userId);

        return new FindFileResponseDto(cardRepository.findMetaDataDtoByCardId(cardId));
    }

    @Transactional
    public DeleteFileResponseDto deleteFile(Long userId, Long workspace, Long fileId) {
        checkWriteRole(workspace,userId);
        FileStorage fileStorage = fileRepository.findById(fileId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_File)
        );
        s3Service.deleteFile(fileStorage.getFileKey());
        fileRepository.deleteById(fileId);
        return new DeleteFileResponseDto(fileId);
    }




    private void checkWriteRole(Long workspaceId, Long userId) {
        Member member = memberRepository.findByUser_IdAndWorkspace_Id(userId, workspaceId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_MEMBER)
        );
        if(member.getRole() == Member.MemberRole.READONLY ){
            throw new BaseException(ErrorCode.NOT_ALLOW_MANAGER);
        }
    }

    private void checkReadRole(Long workspaceId, Long userId) {
        Member member = memberRepository.findByUser_IdAndWorkspace_Id(userId, workspaceId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }


}

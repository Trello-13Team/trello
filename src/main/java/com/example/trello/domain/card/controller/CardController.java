package com.example.trello.domain.card.controller;

import com.example.trello.domain.card.dto.*;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.global.dto.Authentication;
import com.example.trello.global.dto.UploadFileInfo;
import com.example.trello.global.exception.code.SuccessCode;
import com.example.trello.global.response.CommonResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.trello.global.constants.GlobalConstants.USER_AUTH;
import static com.example.trello.global.constants.GlobalConstants.USER_ID;

@RestController()
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards")
    public ResponseEntity<CommonResponse<CreateCardResponseDto>> createCard(@PathVariable("listId") Long listId, @PathVariable("workspaceId") Long workspaceId, @PathVariable("boardId") Long boardId, @RequestBody @Valid CreateCardRequestDto requestDto,
                                                            @SessionAttribute(USER_AUTH) Authentication auth) {

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT,cardService.createCard(requestDto, auth.getId(), workspaceId, boardId, listId) );
    }

    @PatchMapping("workspaces/{workspaceId}/lists/{listId}/cards/{cardId}")
    public ResponseEntity<CommonResponse<SwitchProcessListResponseDto>> switchProcessList(@PathVariable("listId") Long listId, @PathVariable("workspaceId") Long workspaceId, @PathVariable("cardId") Long cardId,
                                                                                   @NotNull @Min(0) @RequestParam Long processListId, @SessionAttribute(USER_AUTH) Authentication auth) {

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE,cardService.switchProcessList(cardId, processListId, workspaceId, auth.getId()) );
    }

    @PutMapping("workspaces/{workspaceId}/lists/{listId}/cards/{cardId}")
    public ResponseEntity<CommonResponse<UpdateCardResponseDto>> updateCard(@PathVariable("listId") Long listId, @PathVariable("workspaceId") Long workspaceId, @PathVariable("cardId") Long cardId,
                                                                            @RequestBody @Valid UpdateCardRequestDto requestDto, @SessionAttribute(USER_AUTH) Authentication auth) {

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE,cardService.updateCard(requestDto, auth.getId(), workspaceId,cardId) );
    }

    @DeleteMapping("workspaces/{workspaceId}/lists/{listId}/cards/{cardId}")
    public ResponseEntity<CommonResponse<DeleteCardResponseDto>> deleteCard(@PathVariable("listId") Long listId, @PathVariable("workspaceId") Long workspaceId, @PathVariable("cardId") Long cardId,
                                                                            @SessionAttribute(USER_AUTH) Authentication auth) {

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE,cardService.deleteCard(auth.getId(), workspaceId, cardId) );
    }

    @GetMapping("workspaces/{workspaceId}/cards")
    public ResponseEntity<CommonResponse<FindCardListResponseDto>> findCardList(@PathVariable("workspaceId") Long workspaceId, @RequestParam(value = "boardId", required = false) @Nullable Long boardId,
                                                                            @SessionAttribute(USER_AUTH) Authentication auth, @RequestBody @Valid FindCardListRequestDto requestBody) {

        return CommonResponse.success(SuccessCode.SUCCESS,cardService.findCardList(auth.getId(),requestBody,workspaceId,boardId) );
    }

    @GetMapping("workspaces/{workspaceId}/cards/{cardId}")
    public ResponseEntity<CommonResponse<CardDetailedInfo>> findCardDetail(@PathVariable("cardId") Long cardId, @PathVariable("workspaceId") Long workspaceId, @SessionAttribute(USER_AUTH) Authentication auth) {
        return CommonResponse.success(SuccessCode.SUCCESS, cardService.findCardDetailedInfo(auth.getId(),workspaceId,cardId) );
    }

    @PostMapping("workspaces/{workspaceId}/cards/{cardId}")
    public ResponseEntity<CommonResponse<UploadFileInfo>> uploadFile(@PathVariable("cardId") Long cardId, @PathVariable("workspaceId") Long workspaceId, @RequestPart(name = "file", required = false) @NotNull MultipartFile file, @SessionAttribute(USER_AUTH) Authentication auth) {
        return CommonResponse.success(SuccessCode.SUCCESS, cardService.uploadFile(auth.getId(),workspaceId, cardId, file) );
    }

    @GetMapping("workspaces/{workspaceId}/cards/{cardId}/files")
    public ResponseEntity<CommonResponse<FindFileResponseDto>> findFiles(@PathVariable("cardId") Long cardId, @PathVariable("workspaceId") Long workspaceId, @SessionAttribute(USER_AUTH) Authentication auth) {
        return CommonResponse.success(SuccessCode.SUCCESS, cardService.getFileMetaData(auth.getId(),workspaceId, cardId) );
    }

    @DeleteMapping("workspaces/{workspaceId}/cards/{cardId}/files/{fileId}")
    public ResponseEntity<CommonResponse<DeleteFileResponseDto>> deleteFile(@PathVariable("cardId") Long cardId, @PathVariable("workspaceId") Long workspaceId,  @PathVariable("fileId") Long fileId, @SessionAttribute(USER_AUTH) Authentication auth) {
        return CommonResponse.success(SuccessCode.SUCCESS, cardService.deleteFile(auth.getId(),workspaceId, fileId) );
    }
}


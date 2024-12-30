package com.example.trello.domain.card.controller;

import com.example.trello.domain.card.dto.CreateCardRequestDto;
import com.example.trello.domain.card.dto.CreateCardResponseDto;
import com.example.trello.domain.card.dto.SwitchProcessListResponseDto;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.global.dto.Authentication;
import com.example.trello.global.exception.code.SuccessCode;
import com.example.trello.global.response.CommonResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.trello.global.constants.GlobalConstants.USER_AUTH;
import static com.example.trello.global.constants.GlobalConstants.USER_ID;

@RestController()
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
//
//    @PostMapping("workspaces/{workspaceId}/lists/{listId}/cards")
//    public ResponseEntity<CommonResponse<CreateCardResponseDto>> createCard(@PathVariable("listId") Long listId, @PathVariable("workspaceId") Long workspaceId, @RequestBody @Valid CreateCardRequestDto requestDto,
//                                                            @SessionAttribute(USER_AUTH) Authentication auth) {
//
//        return CommonResponse.success(SuccessCode.SUCCESS_INSERT,cardService.createCard(requestDto, auth.getId(), workspaceId,listId) );
//    }
//
//    @PatchMapping("workspaces/{workspaceId}/lists/{listId}/cards/{cardId}")
//    public ResponseEntity<CommonResponse<SwitchProcessListResponseDto>> createCard(@PathVariable("listId") Long listId, @PathVariable("workspaceId") Long workspaceId, @PathVariable("cardId") Long cardId,
//                                                                                   @NotNull @Min(0) @RequestParam Long processListId, @SessionAttribute(USER_AUTH) Authentication auth) {
//
//        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE,cardService.switchProcessList(cardId, processListId, workspaceId, auth.getId()) );
//    }
}


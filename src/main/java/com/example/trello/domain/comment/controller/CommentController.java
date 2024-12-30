package com.example.trello.domain.comment.controller;

import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.dto.CommentResponseDto;
import com.example.trello.domain.comment.dto.DeleteCommentResponseDto;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.global.dto.Authentication;
import com.example.trello.global.exception.code.SuccessCode;
import com.example.trello.global.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.trello.global.constants.GlobalConstants.USER_AUTH;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/workspaces/{workspaceId}/cards/{cardId}/comments")
    public ResponseEntity<CommonResponse<CommentResponseDto>> createComment(@PathVariable("workspaceId") Long workspaceId, @PathVariable("cardId") Long cardId,
                                                                            @RequestBody @Valid CommentRequestDto commentRequestDto, @SessionAttribute(USER_AUTH) Authentication auth) {
        return CommonResponse.success(SuccessCode.SUCCESS_INSERT, commentService.createComment(workspaceId,auth.getId(),cardId,commentRequestDto));
    }

    @PutMapping("/workspaces/{workspaceId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> updateComment(@PathVariable("workspaceId") Long workspaceId, @PathVariable("commentId") Long commentId,
                                                                            @RequestBody @Valid CommentRequestDto commentRequestDto, @SessionAttribute(USER_AUTH) Authentication auth) {
        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE, commentService.updateComment(workspaceId,auth.getId(),commentId,commentRequestDto));
    }

    @DeleteMapping("/workspaces/{workspaceId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<DeleteCommentResponseDto>> deleteComment(@PathVariable("workspaceId") Long workspaceId, @PathVariable("commentId") Long commentId,
                                                                                  @RequestBody @Valid CommentRequestDto commentRequestDto, @SessionAttribute(USER_AUTH) Authentication auth){
        return CommonResponse.success(SuccessCode.SUCCESS_DELETE, commentService.deleteComment(workspaceId,auth.getId(),commentId));
    }
}

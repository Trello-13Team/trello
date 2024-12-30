package com.example.trello.domain.comment.service;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.dto.CommentResponseDto;
import com.example.trello.domain.comment.dto.DeleteCommentResponseDto;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.exception.BaseException;
import com.example.trello.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public CommentResponseDto createComment(Long workspaceId, Long userId, Long cardId, CommentRequestDto commentRequestDto ) {
        checkWriteRole(workspaceId,userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_USER)
        );
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_CARD)
        );
        Comment comment = new Comment(commentRequestDto.getContent(), user, card);
        commentRepository.save(comment);
        return  new CommentResponseDto(comment.getId(),comment.getContent(),comment.getUser().getId(),comment.getUser().getName(),comment.getCreatedAt());
    }

    public CommentResponseDto updateComment(Long workspaceId, Long userId, Long commentId, CommentRequestDto commentRequestDto ) {
        checkWriteRole(workspaceId,userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_USER)
        );
        Comment comment = commentRepository.findByIdAndUser(commentId, user).orElseThrow(
                ()-> new BaseException(ErrorCode.NOT_FOUND_COMMENT)
        );
        return  new CommentResponseDto(comment.getId(),comment.getContent(),comment.getUser().getId(),comment.getUser().getName(),comment.getCreatedAt());
    }

    public DeleteCommentResponseDto deleteComment(Long workspaceId, Long userId, Long commentId) {
        checkWriteRole(workspaceId,userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_USER)
        );
        Comment comment = commentRepository.findByIdAndUser(commentId, user).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_COMMENT)
        );
        commentRepository.delete(comment);
        return new DeleteCommentResponseDto(comment.getId());
    }


    private void checkWriteRole(Long workspaceId, Long userId) {
        Member member = memberRepository.findByUser_IdAndWorkspace_Id(userId, workspaceId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_MEMBER)
        );
        if(member.getRole() == Member.MemberRole.MANAGER ){
            throw new BaseException(ErrorCode.NOT_ALLOW_MANAGER);
        }
    }

    private void checkReadRole(Long workspaceId, Long userId) {
        Member member = memberRepository.findByUser_IdAndWorkspace_Id(userId, workspaceId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }
}

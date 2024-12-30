package com.example.trello.domain.comment.repository;

import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUser(Long id, User user);
}

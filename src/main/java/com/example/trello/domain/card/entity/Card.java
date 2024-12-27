package com.example.trello.domain.card.entity;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.user.entity.User;
import com.example.trello.global.entity.BaseCreatedTimeEntity;
import com.example.trello.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Card extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Length(min = 1, max = 30)
    private String title;

    @Column(nullable = false)
    @Length(min = 1, max = 200)
    private String content;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processList_id")
    private ProcessList processList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Card(String title, String content, LocalDateTime dueDate, ProcessList processList, User user) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.processList = processList;
        this.user = user;
    }

    public void update(String title, String content, LocalDateTime dueDate) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
    }

    public void switchProcessList(ProcessList processList) {
        this.processList.getCards().remove(this);
        this.processList = processList;
        processList.getCards().add(this);
    }
}

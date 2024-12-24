package com.example.trello.domain.card.entity;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.user.entity.User;
import com.example.trello.global.entity.BaseCreatedTimeEntity;
import com.example.trello.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
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
    private DateTime dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private ProcessList list;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}

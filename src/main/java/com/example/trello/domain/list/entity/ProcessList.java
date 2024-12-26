package com.example.trello.domain.list.entity;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
public class ProcessList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Length(min = 1, max = 30)
    private String title;

    @Column
    private Long Order;

    @Column
    @OneToMany(mappedBy = "process_list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

}

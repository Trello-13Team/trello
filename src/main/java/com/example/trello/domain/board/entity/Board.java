package com.example.trello.domain.board.entity;

import com.example.trello.domain.board.dto.BoardRequestDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.global.entity.BaseTimeEntity;
import com.example.trello.global.entity.FileStorage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    @Length(min = 1, max = 30)
    private String title;

    @Column(nullable = true, length = 255)
    @Length(min = 1, max = 200)
    private String color;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProcessList> lists = new ArrayList<>();


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

    @OneToOne
    @JoinColumn
    private FileStorage fileStorage = null;

    public Board(Workspace workspace,String title, String color) {
        this.workspace = workspace;
        this.title = title;
        this.color = color;
    }

    public void updateBoard(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.color = boardRequestDto.getColor();
    }

    public void changeImage(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }
}

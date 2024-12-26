package com.example.trello.domain.member.entity;

import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import jdk.dynalink.beans.StaticClass;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workSpace;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public enum MemberRole{
        ADMIN, DEVELOPER, MANAGER;
    }
}

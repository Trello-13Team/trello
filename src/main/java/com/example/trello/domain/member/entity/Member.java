package com.example.trello.domain.member.entity;

import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import jdk.dynalink.beans.StaticClass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public enum MemberRole{
        ADMIN, DEVELOPER, MANAGER;
    }
}

package com.example.trello.domain.member.dto;

import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class MemberRequestDto {
    private Member.MemberRole memberRole;
    private Long workspaceId;

    public Member toEntity(Workspace workspace, User user) {
        return new Member(
                this.memberRole,
                workspace,
                user
        );
    }

}

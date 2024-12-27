package com.example.trello.domain.member.repository;

import com.example.trello.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser_IdAndWorkspace_Id(Long userId, Long workspaceId);
}

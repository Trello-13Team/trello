package com.example.trello.domain.member.repository;

import com.example.trello.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Member m JOIN m.user u WHERE u.id = :userId")
    List<Member> findMembersByUserId(@Param("userId") Long userId);

    @Query("SELECT m " +
            "FROM Member m " +
            "WHERE m.user.id = :userId AND m.workspace.id = :workspaceId")
    Member findMemberByUserIdAndWorkspaceId(@Param("userId")Long userid, @Param("workspaceId") Long workspaceId);

    Member findByUserId(Long userId);

}

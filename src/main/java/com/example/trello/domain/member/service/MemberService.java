package com.example.trello.domain.member.service;

import com.example.trello.domain.member.dto.MemberRequestDto;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import com.example.trello.global.util.SessionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class MemberService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final SessionUtil sessionUtil;

    public MemberService(UserRepository userRepository, MemberRepository memberRepository, WorkspaceRepository workspaceRepository, SessionUtil sessionUtil) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.workspaceRepository = workspaceRepository;
        this.sessionUtil = sessionUtil;
    }

    @Transactional
    public ResponseEntity<?> memberInvite(String email, MemberRequestDto requestDto) {
        //이메일 존재 여부 확인
        if(!userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다.");
        }

        //이메일에 맞는 유저 객체 생성
        User user = userRepository.findByEmail(email);
        //현재 접속중인 멤버의 유저Id로 멤버 객체 생성
        Member member = memberRepository.findMemberByUserIdAndWorkspaceId(sessionUtil.getUserId(), requestDto.getWorkspaceId());
        Workspace workspace = workspaceRepository.findById(requestDto.getWorkspaceId()).orElseThrow();

        //관리자만 자신의 워크스페이스에 멤버를 초대할 수 있어야함
        if(Objects.isNull(member) || !member.getRole().equals(Member.MemberRole.WORKSPACE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "관리자만 초대를 할 수 있습니다.");
        }

        //초대 중복 방지
        if(memberRepository.existsByUserId(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 초대된 멤버입니다.");
        };

        //멤버 추가
        member = memberRepository.save(requestDto.toEntity(workspace, user));

        return new ResponseEntity<>(member.getRole().toString() + "멤버 권한으로 초대했습니다." ,HttpStatus.OK);
    }


}

package com.example.trello.domain.workspace.service;

import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.dto.WorkspaceRequestBody;
import com.example.trello.domain.workspace.dto.WorkspaceResponseDto;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import com.example.trello.global.util.SessionUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class WorkspaceService {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final MemberRepository memberRepository;
    private final SessionUtil sessionUtil;

    public WorkspaceService(UserRepository userRepository, WorkspaceRepository workspaceRepository, MemberRepository memberRepository, SessionUtil sessionUtil) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.memberRepository = memberRepository;
        this.sessionUtil = sessionUtil;
    }

    //워크스페이스 생성
    @Transactional
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(WorkspaceRequestBody requestBody) {
        User user = userRepository.findById(sessionUtil.getUserId()).orElseThrow();

        Workspace workspace = workspaceRepository.save(requestBody.toEntity(user));

        //워크스페이스 생성시 자신은 WORKSPACE를 가진 관리자로 멤버로 추가
        memberRepository.save(new Member(Member.MemberRole.WORKSPACE, workspace, user));

        WorkspaceResponseDto workspaceResponseDto = new WorkspaceResponseDto(workspace.getId(), workspace.getName(), workspace.getDescription());
        return new ResponseEntity<>(workspaceResponseDto, HttpStatus.CREATED);
    }

    //워크스페이스 수정
    @Transactional
    public ResponseEntity<WorkspaceResponseDto> patchWorkspace(Long id, WorkspaceRequestBody requestBody) {
        //사용 멤버 역할 검사
        validMemberRole(id);

        Workspace workspace = workspaceRepository.findById(id).orElseThrow();
        //update 후 db 수정
        workspace.updateWorkspace(requestBody.getName(), requestBody.getDescription());
        workspaceRepository.save(workspace);

        WorkspaceResponseDto workspaceResponseDto = new WorkspaceResponseDto(workspace.getId(), workspace.getName(), workspace.getDescription());

        return new ResponseEntity<>(workspaceResponseDto, HttpStatus.OK);
    }

    //워크스페이스 조회
    @Transactional
    public ResponseEntity<List<WorkspaceResponseDto>> findWorkspaceByUser() {
        User user = userRepository.findById(sessionUtil.getUserId()).orElseThrow();
        List<Member> members = memberRepository.findMembersByUserId(user.getId());

       List<Workspace> workspaces = members.stream().map(Member::getWorkspace).toList();

       List<WorkspaceResponseDto> responseDtos = new ArrayList<>();

       for (Workspace workspace : workspaces) {
           WorkspaceResponseDto workspaceResponseDto = WorkspaceResponseDto.toDto(workspace);
           responseDtos.add(workspaceResponseDto);
       }

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    //워크스페이스 삭제
    @Transactional
    public void delete(Long id) {
        //사용 멤버 역할 검사
        validMemberRole(id);

        Workspace workspace = workspaceRepository.findById(id).orElseThrow();
        if(workspace.getUser().getId().equals(sessionUtil.getUserId())) {
            workspaceRepository.delete(workspace);
        }
    }

    //멤버 역할 검사
    public void validMemberRole(Long id) {
        User user = userRepository.findById(sessionUtil.getUserId()).orElseThrow();
        Member member = memberRepository.findMemberByUserIdAndWorkspaceId(user.getId(), id);
        if(Objects.isNull(member) || !member.getRole().equals(Member.MemberRole.WORKSPACE)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "워크스페이스 역할을 가진 관리자만 수정 가능합니다.");
        }
    }
}

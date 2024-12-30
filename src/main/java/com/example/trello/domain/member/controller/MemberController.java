package com.example.trello.domain.member.controller;

import com.example.trello.domain.member.dto.MemberRequestDto;
import com.example.trello.domain.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{email}")
    public ResponseEntity<?> memberInvite(@PathVariable String email, @RequestBody MemberRequestDto requestDto) {
        return memberService.memberInvite(email, requestDto);
    }
}

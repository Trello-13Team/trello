package com.example.trello.domain.user.controller;

import com.example.trello.domain.user.dto.DeleteRequestDto;
import com.example.trello.domain.user.dto.LoginRequestDto;
import com.example.trello.domain.user.dto.UserRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.constants.GlobalConstants;
import com.example.trello.global.dto.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            return new ResponseEntity<>("중복되거나 탈퇴한 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        UserResponseDto userResponseDto = userService.createUser(requestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginWithEmail(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Authentication authentication = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute(GlobalConstants.USER_AUTH, authentication);
        return new ResponseEntity<>("로그인되었습니다. ", HttpStatus.OK);
    }

    //로그아웃
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestBody DeleteRequestDto requestDto, HttpSession session) {
        session.getAttribute(id.toString());
        return userService.deleteUser(id, requestDto, session);
    }

}

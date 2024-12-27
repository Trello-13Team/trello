package com.example.trello.domain.user.service;

import com.example.trello.domain.user.dto.DeleteRequestDto;
import com.example.trello.domain.user.dto.LoginRequestDto;
import com.example.trello.domain.user.dto.UserRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.dto.Authentication;
import com.example.trello.global.entity.Role;
import com.example.trello.global.util.PasswordEncoder;
import com.example.trello.global.validation.PasswordValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public UserResponseDto createUser(UserRequestDto requestDto) {
        Pattern validPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher validPassMatcher = validPattern.matcher(requestDto.getEmail());

        if (!validPassMatcher.find()) {
            throw new IllegalArgumentException("이메일 형식이 아닙니다.");
        }

        String encodedPassword = PasswordEncoder.encode(requestDto.getPassword());
        requestDto.updatePassword(encodedPassword);

        userRepository.save(requestDto.toEntity());

        return new UserResponseDto(requestDto.getName(), requestDto.getEmail(), Role.of(requestDto.getRole()));
    }

    //로그인
    public Authentication loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if(user.getRole() == Role.SECESSION) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다.");
        }

        if (user == null || !PasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 이름 혹은 잘못된 비밀번호");
        }
        return new Authentication(user.getId(), user.getRole());
    }

    //회원탈퇴
    public ResponseEntity<?> deleteUser(Long id, DeleteRequestDto requestDto, HttpSession session) {
        User user = userRepository.findById(id).orElseThrow();
        if(user.getRole() == Role.SECESSION) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다.");
        }

        //비밀번호 일치 검사
        if (PasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            user.delete(Role.SECESSION);
            userRepository.save(user);
        } else {
            return new ResponseEntity<>("비밀번호가 틀립니다.", HttpStatus.BAD_REQUEST);
        }
        session.invalidate();
        return new ResponseEntity<>("회원 탈퇴가 완료되었습니다.", HttpStatus.OK);
    }
}

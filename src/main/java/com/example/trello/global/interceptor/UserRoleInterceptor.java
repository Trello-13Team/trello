package com.example.trello.global.interceptor;

import com.example.trello.global.constants.GlobalConstants;
import com.example.trello.global.dto.Authentication;
import com.example.trello.global.entity.Role;
import com.example.trello.global.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnauthorizedException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "세션이 끊어졌습니다.");
        }

        Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
        Role role = authentication.getRole();

        if (role != Role.USER) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "user 권한이 필요합니다.");
        }

        return true;
    }
}
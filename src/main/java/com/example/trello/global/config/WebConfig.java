package com.example.trello.global.config;

import com.example.trello.global.entity.Role;
import com.example.trello.global.filter.AuthFilter;
import com.example.trello.global.filter.RoleFilter;
import com.example.trello.global.interceptor.AdminRoleInterceptor;
import com.example.trello.global.interceptor.AuthInterceptor;
import com.example.trello.global.interceptor.UserRoleInterceptor;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {


    private static final String[] AUTH_REQUIRED_PATH_PATTERNS = {"/users/logout", "/workspaces/**"};
    private static final String[] ADMIN_ROLE_REQUIRED_PATH_PATTERNS = {"/workspaces/admin"};

    private final AuthInterceptor authInterceptor;
    private final AdminRoleInterceptor adminRoleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(AUTH_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE);

        registry.addInterceptor(adminRoleInterceptor)
                .addPathPatterns(ADMIN_ROLE_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE + 1);

    }


}


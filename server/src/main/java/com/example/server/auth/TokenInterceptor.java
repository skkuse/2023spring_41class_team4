package com.example.server.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private static final String TOKEN_NAME = "X-Auth-Token";
    private final TokenResolver tokenResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(TOKEN_NAME);
        log.info("Token: {}", token);

        CurrentUser user = tokenResolver.decode(token);
        if (request.getRequestURI().startsWith("/teacher")) {
            if (user.getType().equals(UserType.STUDENT)) {
                throw new RuntimeException("Not Authorized");
            }
        } else {
            if (user.getType().equals(UserType.TEACHER)) {
                throw new RuntimeException("Not Authorized");
            }
        }

        CurrentUserHolder.set(user);
        return true;
    }
}

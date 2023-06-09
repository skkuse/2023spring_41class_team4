package com.example.server.auth;

import com.example.server.exceptions.NotAuthenticated;
import com.example.server.exceptions.NotAuthorized;
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
        if (token == null) {
            throw new NotAuthenticated(request.getRequestURI());
        }
        log.info("Token: {}", token);

        CurrentUser user = tokenResolver.decode(token);
        if (request.getRequestURI().startsWith("/teacher")) {
            if (user.getType().equals(UserType.STUDENT)) {
                throw new NotAuthorized("teacher", request.getRequestURI());
            }
        } else {
            if (user.getType().equals(UserType.TEACHER)) {
                throw new NotAuthorized("student", request.getRequestURI());
            }
        }

        CurrentUserHolder.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentUserHolder.clear();
    }
}

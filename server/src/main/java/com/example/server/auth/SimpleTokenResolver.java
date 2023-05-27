package com.example.server.auth;

import org.springframework.stereotype.Component;

@Component
public class SimpleTokenResolver implements TokenResolver {

    @Override
    public String encode(CurrentUser user) {
        return user.getType().name() + user.getId();
    }

    @Override
    public CurrentUser decode(String token) {
        UserType type = UserType.valueOf(token.substring(0, 7));
        Long id = Long.parseLong(token.substring(7));
        return new CurrentUser(id, type);
    }
}

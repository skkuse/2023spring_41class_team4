package com.example.server.auth;

public interface TokenResolver {

    String encode(CurrentUser user);
    CurrentUser decode(String token);
}

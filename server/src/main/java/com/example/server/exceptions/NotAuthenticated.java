package com.example.server.exceptions;

public class NotAuthenticated extends RuntimeException {
    
    public NotAuthenticated() {
        super();
    }

    public NotAuthenticated(String url) {
        super("Access token not exist " + url);
    }

    public NotAuthenticated(String url, Throwable cause) {
        super("Access token not exist " + url, cause);
    }
}

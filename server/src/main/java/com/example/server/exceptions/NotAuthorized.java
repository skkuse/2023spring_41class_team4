package com.example.server.exceptions;

public class NotAuthorized extends RuntimeException {

    public NotAuthorized() {
        super();
    }

    public NotAuthorized(String target, Object id) {
        super("Cannot access " + target + " " + id);
    }

    public NotAuthorized(String target, Object id, Throwable cause) {
        super("Cannot access " + target + " " + id, cause);
    }
}

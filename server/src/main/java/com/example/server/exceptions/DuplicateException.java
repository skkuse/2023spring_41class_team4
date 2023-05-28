package com.example.server.exceptions;

public abstract class DuplicateException extends RuntimeException {

    public DuplicateException() {
        super();
    }

    public DuplicateException(String key) {
        super(key + " already exist");
    }

    public DuplicateException(String key, Throwable cause) {
        super(key + " already exist");
    }
}

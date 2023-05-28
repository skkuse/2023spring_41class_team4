package com.example.server.exceptions;

public abstract class NoDataException extends RuntimeException {

    public NoDataException() {
        super();
    }

    public NoDataException(String target, Object id) {
        super(target + " " + id + " not exist");
    }

    public NoDataException(String target, Object id, Throwable cause) {
        super(target + " " + id + " not exist", cause);
    }
}

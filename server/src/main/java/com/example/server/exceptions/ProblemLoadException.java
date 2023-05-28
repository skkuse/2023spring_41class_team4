package com.example.server.exceptions;

public class ProblemLoadException extends RuntimeException {

    public ProblemLoadException() {
        super("Cannot load problem");
    }

    public ProblemLoadException(String id) {
        super("Cannot load problem id: " + id);
    }

    public ProblemLoadException(String id, Throwable cause) {
        super("Cannot load problem id: " + id, cause);
    }
}

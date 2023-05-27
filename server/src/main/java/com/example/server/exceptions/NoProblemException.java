package com.example.server.exceptions;

public class NoProblemException extends NoDataException {

    public NoProblemException() {
        super();
    }

    public NoProblemException(Object id) {
        super("Problem", id);
    }

    public NoProblemException(Object id, Throwable cause) {
        super("Problem", id, cause);
    }
}

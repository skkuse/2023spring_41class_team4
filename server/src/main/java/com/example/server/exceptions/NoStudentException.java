package com.example.server.exceptions;

public class NoStudentException extends NoDataException {

    public NoStudentException() {
        super();
    }

    public NoStudentException(Object id) {
        super("Student", id);
    }

    public NoStudentException(Object id, Throwable cause) {
        super("Student", id, cause);
    }
}

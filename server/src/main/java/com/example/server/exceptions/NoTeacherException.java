package com.example.server.exceptions;

public class NoTeacherException extends NoDataException {

    public NoTeacherException() {
        super();
    }

    public NoTeacherException(Object id) {
        super("Teacher", id);
    }

    public NoTeacherException(Object id, Throwable cause) {
        super("Teacher", id, cause);
    }
}

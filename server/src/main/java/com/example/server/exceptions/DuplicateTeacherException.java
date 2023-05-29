package com.example.server.exceptions;

public class DuplicateTeacherException extends DuplicateException {

    public DuplicateTeacherException() {
        super();
    }

    public DuplicateTeacherException(String key) {
        super("Teacher " + key + " already exist");
    }

    public DuplicateTeacherException(String key, Throwable cause) {
        super("Teacher " + key + " already exist");
    }
}

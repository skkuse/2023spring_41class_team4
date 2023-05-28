package com.example.server.exceptions;

public class DuplicateStudentException extends DuplicateException {

    public DuplicateStudentException() {
        super();
    }

    public DuplicateStudentException(String key) {
        super("Student " + key + " already exist");
    }

    public DuplicateStudentException(String key, Throwable cause) {
        super("Student " + key + " already exist");
    }
}

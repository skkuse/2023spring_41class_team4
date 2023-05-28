package com.example.server.exceptions;

public class NoSubmissionException extends NoDataException {

    public NoSubmissionException() {
        super();
    }

    public NoSubmissionException(Object id) {
        super("Submission", id);
    }

    public NoSubmissionException(Object id, Throwable cause) {
        super("Submission", id, cause);
    }
}

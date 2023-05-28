package com.example.server.exceptions;

import com.example.server.utils.DateUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ExceptionResponseAdvice {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<ExceptionResponse> noDataExceptionResponse(NoDataException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorized.class)
    public ResponseEntity<ExceptionResponse> notAuthorizedResponse(NotAuthorized exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotAuthenticated.class)
    public ResponseEntity<ExceptionResponse> notAuthorizedResponse(NotAuthenticated exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProblemLoadException.class)
    public ResponseEntity<ExceptionResponse> notAuthorizedResponse(ProblemLoadException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ExceptionResponse> notAuthorizedResponse(DuplicateException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception), HttpStatus.CONFLICT);
    }

    @Getter
    private static class ExceptionResponse {

        private String message;
        private String timestamp;

        public ExceptionResponse(Exception e) {
            this.message = e.getMessage();
            this.timestamp = DateUtils.formattedDate(LocalDateTime.now());
        }
    }
}

package com.example.server.student.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemSubmitRequest {

    private String language;
    private String content;
}

package com.example.server.student.problem.dto;

import com.example.server.student.problem.Problem;
import lombok.Getter;

@Getter
public class ProblemResponse {

    private Long id;
    private String title;
    private String content;

    public ProblemResponse(Problem problem) {
        this.id = problem.getId();
        this.title = problem.getTitle();
        this.content = problem.getContent();
    }
}

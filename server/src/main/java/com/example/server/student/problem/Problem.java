package com.example.server.student.problem;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    private int problemNumber;

    private String title;

    private String content;

    public Problem(int problemNumber, String title, String content) {
        this.problemNumber = problemNumber;
        this.title = title;
        this.content = content;
    }
}

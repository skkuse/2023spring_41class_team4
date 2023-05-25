package com.example.server.student.problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id
    @Column(name = "problem_id")
    private Long id;

    private String title;

    private String content;

    public Problem(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}

package com.example.server.teacher.feedback;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;

    private String overview;

    @Embedded
    private Achievement achievement;

    public Feedback(String overview, Achievement achievement) {
        this.overview = overview;
        this.achievement = achievement;
    }
}

package com.example.server.teacher.feedback;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    private int efficiency = 0;
    private int readability = 0;
    private int correctness = 0;
    private int scalability = 0;
    private int modularity = 0;
    private int security = 0;
}

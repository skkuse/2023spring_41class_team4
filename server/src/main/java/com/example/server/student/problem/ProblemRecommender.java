package com.example.server.student.problem;

import java.util.List;

public interface ProblemRecommender {
    List<Long> recommend(String solvedProblemNumbers);
}

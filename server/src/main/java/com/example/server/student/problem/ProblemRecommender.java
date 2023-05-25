package com.example.server.student.problem;

import com.example.server.student.problem.dto.RecommendResponse;

public interface ProblemRecommender {
    RecommendResponse recommend(String solvedProblemNumbers);
}

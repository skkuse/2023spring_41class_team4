package com.example.server.student.problem;

import com.example.server.student.problem.dto.SolvedacProblemResponse;

public interface ProblemFetcher {
    SolvedacProblemResponse fetch(String problemId);
}

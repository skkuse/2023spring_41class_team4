package com.example.server.student.problem;

import com.example.server.student.problem.dto.SolvedacResponse;

public interface ProblemLoader {
    SolvedacResponse load(String bojId);
}

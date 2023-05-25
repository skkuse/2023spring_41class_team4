package com.example.server.student.problem;

import static org.junit.jupiter.api.Assertions.*;

import com.example.server.student.problem.dto.SolvedacProblemResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProblemFetcherTest {

    @Autowired
    private ProblemFetcher problemFetcher;

    @Test
    void ProblemFetcherTest() {
        // given
        final SolvedacProblemResponse response = problemFetcher.fetch("2212");

        // when
        return;

        // then
    }
}
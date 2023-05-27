package com.example.server.student.problem;

import com.example.server.student.problem.dto.SolvedacProblemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ProblemFetcherImpl implements ProblemFetcher {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Override
    public SolvedacProblemResponse fetch(String problemId) {
        log.info("fetch problemId: {}", problemId);
        return REST_TEMPLATE.exchange(
                "https://solved.ac/api/v3/problem/show?problemId=" + problemId,
                HttpMethod.GET,
                null,
                SolvedacProblemResponse.class
        ).getBody();
    }
}

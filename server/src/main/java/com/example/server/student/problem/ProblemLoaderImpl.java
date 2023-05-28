package com.example.server.student.problem;

import com.example.server.exceptions.ProblemLoadException;
import com.example.server.student.problem.dto.SolvedacResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProblemLoaderImpl implements ProblemLoader{

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Override
    public SolvedacResponse load(String bojId) {
        try {
            return REST_TEMPLATE.exchange(
                    "https://solved.ac/api/v3/user/top_100?handle=" + bojId,
                    HttpMethod.GET,
                    null,
                    SolvedacResponse.class
            ).getBody();
        } catch (HttpClientErrorException e) {
            throw new ProblemLoadException(bojId);
        }
    }
}

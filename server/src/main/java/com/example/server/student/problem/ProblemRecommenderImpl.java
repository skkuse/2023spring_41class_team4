package com.example.server.student.problem;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProblemRecommenderImpl implements ProblemRecommender {

    @Value("${spring.openai.api-key}")
    private String apiKey;

    @Override
    public List<Integer> recommend(String solvedProblemNumbers) {
        final OpenAiService openAiService = new OpenAiService(apiKey);
        final CompletionRequest request = CompletionRequest.builder()
                .prompt(solvedProblemNumbers + "지금까지 풀었던 백준 문제야, 다음에 풀 백준 문제 5문제를 추천해줘. 다른 설명은 없이 번호만 5개 출력해줘.")
                .model("text-davinci-003")
                .echo(false)
                .build();

        final CompletionChoice completionChoice = openAiService.createCompletion(request).getChoices().get(0);
        final String response = completionChoice.getText().trim();
        log.info("Problem Recommend Response: {}", response);

        final List<String> recommends = List.of(response.split(","));
        log.info("recommends: {}", recommends);

        return recommends.stream()
                .map(this::parse)
                .toList();
    }

    private Integer parse(String numberString) {
        return Integer.parseInt(numberString.replaceAll("\\s", ""));
    }
}

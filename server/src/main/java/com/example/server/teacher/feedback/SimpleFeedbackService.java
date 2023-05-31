package com.example.server.teacher.feedback;

import com.example.server.student.submission.Submission;
import com.example.server.teacher.feedback.dto.OpenAiAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SimpleFeedbackService implements FeedbackService {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${spring.openai.api-key}")
    private String apiKey;

    private final FeedbackRepository feedbackRepository;

    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Feedback> requestFeedback(Submission submission) {
        final String code = submission.getCode();

        final String overview = openAiResponse(code + "이 코드에 대해 피드백해줘");

        log.info("Problem Feedback Response: {}", overview);

        final String achievement = openAiResponse(
                code + "이 코드의 efficiency, readability, correctness, scalability, modularity, security를 0~100점 사이로 평가해줘."
                        + "efficiency는 코드의 효율성, readability는 코드의 가독성, correctness는 코드의 정확성, scalability는 코드의 확장성, modularity는 코드의 모듈성, security는 코드의 보안성을 의미해."
                        + "efficiency는 0점이면 효율성이 전혀 없는 코드고 100점이면 효율성이 최고인 코드야."
                        + "readability는 0점이면 가독성이 전혀 없는 코드고 100점이면 가독성이 최고인 코드야."
                        + "correctness는 0점이면 정확성이 전혀 없는 코드고 100점이면 정확성이 최고인 코드야."
                        + "scalability는 0점이면 확장성이 전혀 없는 코드고 100점이면 확장성이 최고인 코드야."
                        + "modularity는 0점이면 모듈성이 전혀 없는 코드고 100점이면 모듈성이 최고인 코드야."
                        + "security는 0점이면 보안성이 전혀 없는 코드고 100점이면 보안성이 최고인 코드야."
                        + "추가적인 설명없이 <기준: 점수>의 형태로만 출력해줘.");

        log.info("Problem Achievement Response: {}", achievement);

        return CompletableFuture.completedFuture(new Feedback(overview, parseFromAchievement(achievement)));
    }

    private Achievement parseFromAchievement(String achievement) {
        final String[] strings = achievement.split("\n");

        final int efficiency = Integer.parseInt(strings[0].split(":")[1]);
        final int readability = Integer.parseInt(strings[1].split(":")[1]);
        final int correctness = Integer.parseInt(strings[2].split(":")[1]);
        final int scalability = Integer.parseInt(strings[3].split(":")[1]);
        final int modularity = Integer.parseInt(strings[4].split(":")[1]);
        final int security = Integer.parseInt(strings[5].split(":")[1]);

        return new Achievement(efficiency, readability, correctness, scalability, modularity, security);
    }

    private String openAiResponse(String prompt) {
        // Set the request headers.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + apiKey);

        // Create the request body.
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");

        Map<String, String> messages = new HashMap<>();
        messages.put("role", "user");
        messages.put("content", prompt);
        requestBody.put("messages", List.of(messages));

        final OpenAiAPIResponse response = REST_TEMPLATE.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                OpenAiAPIResponse.class
        ).getBody();

        assert response != null;
        return response.getChoices().get(0).getMessage().getContent();
    }
}

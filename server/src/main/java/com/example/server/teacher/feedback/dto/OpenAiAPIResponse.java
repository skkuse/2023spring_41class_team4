package com.example.server.teacher.feedback.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiAPIResponse {
    private List<OpenAiChoiceResponse> choices;
}

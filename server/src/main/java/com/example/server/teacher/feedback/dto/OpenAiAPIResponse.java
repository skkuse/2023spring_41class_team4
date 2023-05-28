package com.example.server.teacher.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiAPIResponse {
    private List<OpenAiChoiceResponse> choices;
}

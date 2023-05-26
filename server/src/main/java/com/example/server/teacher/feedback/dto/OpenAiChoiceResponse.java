package com.example.server.teacher.feedback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpenAiChoiceResponse {
    private OpenAiMessageResponse message;
}

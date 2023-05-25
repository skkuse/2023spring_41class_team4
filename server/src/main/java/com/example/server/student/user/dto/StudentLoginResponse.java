package com.example.server.student.user.dto;

import lombok.Getter;

@Getter
public class StudentLoginResponse {

    private String token;

    public StudentLoginResponse(String token) {
        this.token = token;
    }
}

package com.example.server.student.user.dto;

import lombok.Getter;

@Getter
public class StudentLoginRequest {

    private String email;
    private String password;
}

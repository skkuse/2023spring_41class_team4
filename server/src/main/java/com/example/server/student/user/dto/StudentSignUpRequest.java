package com.example.server.student.user.dto;

import lombok.Getter;

@Getter
public class StudentSignUpRequest {

    private String email;
    private String password;
    private String name;
    private String githubAccount;
    private String teacherCode;
}

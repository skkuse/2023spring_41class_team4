package com.example.server.teacher.user.dto;

import lombok.Getter;

@Getter
public class TeacherSignUpRequest {

    private String email;
    private String password;
    private String name;
}

package com.example.server.teacher.user.dto;

import lombok.Getter;

@Getter
public class TeacherLoginRequest {

    private String email;
    private String password;
}

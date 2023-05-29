package com.example.server.teacher.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeacherLoginRequest {

    private String email;
    private String password;
}

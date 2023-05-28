package com.example.server.teacher.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeacherSignUpRequest {

    private String email;
    private String password;
    private String name;
}

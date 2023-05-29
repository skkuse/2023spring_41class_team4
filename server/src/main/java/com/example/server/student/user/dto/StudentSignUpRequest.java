package com.example.server.student.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentSignUpRequest {

    private String email;
    private String password;
    private String name;
    private String bojId;
    private String teacherCode;
}

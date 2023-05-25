package com.example.server.teacher.user.dto;

import com.example.server.teacher.user.Teacher;
import lombok.Getter;

@Getter
public class TeacherInfoResponse {

    private Long id;
    private String name;
    private String email;
    private String teacherCode;

    public TeacherInfoResponse(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.teacherCode = teacher.getTeacherCode();
    }
}

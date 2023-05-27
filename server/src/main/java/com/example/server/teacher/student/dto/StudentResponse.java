package com.example.server.teacher.student.dto;

import com.example.server.student.user.Student;
import lombok.Getter;

@Getter
public class StudentResponse {

    private Long id;
    private String name;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
    }
}

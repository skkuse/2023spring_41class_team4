package com.example.server.student.user.dto;

import com.example.server.student.user.Student;
import com.example.server.teacher.user.Teacher;
import lombok.Getter;

@Getter
public class StudentInfoResponse {

    private Long id;
    private String name;
    private String email;
    private TeacherInfo teacher;

    public StudentInfoResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.teacher = new TeacherInfo(student.getTeacher());
    }

    @Getter
    public static class TeacherInfo {
        private Long id;
        private String name;

        public TeacherInfo(Teacher teacher) {
            this.id = teacher.getId();
            this.name = teacher.getName();
        }
    }
}

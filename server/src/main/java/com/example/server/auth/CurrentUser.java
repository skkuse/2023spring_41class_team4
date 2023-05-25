package com.example.server.auth;

import com.example.server.student.user.Student;
import com.example.server.teacher.user.Teacher;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CurrentUser {

    private Long id;
    private UserType type;

    public CurrentUser(Student student) {
        this.id = student.getId();
        this.type = UserType.STUDENT;
    }

    public CurrentUser(Teacher teacher) {
        this.id = teacher.getId();
        this.type = UserType.TEACHER;
    }
}

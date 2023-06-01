package com.example.server.teacher.user;

import com.example.server.utils.TimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(unique = true)
    private String teacherCode;

    public Teacher(String email, String password, String name, String teacherCode) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.teacherCode = teacherCode;
    }
}

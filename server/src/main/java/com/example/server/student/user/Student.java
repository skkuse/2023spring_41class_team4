package com.example.server.student.user;

import com.example.server.teacher.user.Teacher;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String githubAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Student(String email, String password, String name, String githubAccount, Teacher teacher) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.githubAccount = githubAccount;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }
}

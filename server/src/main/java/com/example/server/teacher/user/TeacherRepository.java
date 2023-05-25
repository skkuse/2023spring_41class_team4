package com.example.server.teacher.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmailAndPassword(String email, String password);

    Optional<Teacher> findByTeacherCode(String teacherCode);

    boolean existsByTeacherCode(String teacherCode);
}

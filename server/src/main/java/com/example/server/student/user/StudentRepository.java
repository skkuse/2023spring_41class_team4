package com.example.server.student.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmailAndPassword(String email, String password);

    Page<Student> findAllByTeacherId(Long teacherId, Pageable pageable);
}

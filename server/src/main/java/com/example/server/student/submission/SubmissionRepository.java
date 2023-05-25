package com.example.server.student.submission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("select s from Submission s where s.student.id = :studentId")
    Page<Submission> findAllByStudentId(Long studentId, Pageable pageable);

    @Query("select s from Submission s where s.student.teacher.id = :teacherId")
    Page<Submission> findAllByTeacherId(Long teacherId, Pageable pageable);
}

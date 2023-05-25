package com.example.server.student.problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuggestedProblemRepository extends JpaRepository<SuggestedProblem, Long> {

    Page<SuggestedProblem> findAllByStudentId(Long studentId, Pageable pageable);

    Optional<SuggestedProblem> findByStudentIdAndProblemId(Long studentId, Long problemId);

    boolean existsByStudentIdAndProblemId(Long studentId, Long problemId);
}

package com.example.server.student.problem;

import com.example.server.student.problem.dto.ProblemListResponse;
import com.example.server.student.problem.dto.ProblemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {

    private final SuggestedProblemRepository suggestedProblemRepository;

    public ProblemListResponse getProblemList(Long studentId, Pageable pageable) {
        Page<SuggestedProblem> problems = suggestedProblemRepository.findAllByStudentId(studentId, pageable);
        return new ProblemListResponse(problems);
    }

    public ProblemResponse getProblem(Long studentId, Long problemId) {
        SuggestedProblem problem = suggestedProblemRepository.findByStudentIdAndProblemId(studentId, problemId)
                .orElseThrow(() -> new RuntimeException("No Problem Exist"));
        return new ProblemResponse(problem.getProblem());
    }
}

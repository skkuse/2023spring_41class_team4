package com.example.server.student.problem;

import com.example.server.auth.CurrentUserHolder;
import com.example.server.student.problem.dto.ProblemListResponse;
import com.example.server.student.problem.dto.ProblemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<ProblemListResponse> getProblemList(Pageable pageable) {
        return ResponseEntity.ok(problemService.getProblemList(CurrentUserHolder.getUserId(), pageable));
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemResponse> getProblem(@PathVariable Long problemId) {
        return ResponseEntity.ok(problemService.getProblem(CurrentUserHolder.getUserId(), problemId));
    }
}

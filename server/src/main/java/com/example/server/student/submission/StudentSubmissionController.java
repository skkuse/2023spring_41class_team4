package com.example.server.student.submission;

import com.example.server.auth.CurrentUserHolder;
import com.example.server.student.problem.dto.ProblemSubmitRequest;
import com.example.server.student.submission.dto.SubmissionListResponse;
import com.example.server.student.submission.dto.SubmissionResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudentSubmissionController {

    private final StudentSubmissionService submissionService;

    @GetMapping("/submissions")
    public ResponseEntity<SubmissionListResponse> getSubmissionList(Pageable pageable) {
        return ResponseEntity.ok(submissionService.getSubmissionList(CurrentUserHolder.getUserId(), pageable));
    }

    @GetMapping("/submissions/{submissionId}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable Long submissionId) {
        return ResponseEntity.ok(submissionService.getSubmission(CurrentUserHolder.getUserId(), submissionId));
    }

    @PostMapping("/problems/{problemId}/submit")
    public ResponseEntity<Void> submit(@PathVariable Long problemId, @RequestBody ProblemSubmitRequest request, HttpServletResponse response) {
        Submission submission = submissionService.submit(CurrentUserHolder.getUserId(), problemId, request.getLanguage(), request.getContent());
        response.addHeader("Location", "/submissions/" + submission.getId());
        return ResponseEntity.ok().build();
    }
}

package com.example.server.teacher.submission;

import com.example.server.auth.CurrentUserHolder;
import com.example.server.student.submission.dto.SubmissionListResponse;
import com.example.server.teacher.submission.dto.CommentRequest;
import com.example.server.teacher.submission.dto.TeacherSubmissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher/submissions")
public class TeacherSubmissionController {

    private final TeacherSubmissionService submissionService;

    @GetMapping
    public ResponseEntity<SubmissionListResponse> getSubmissionList(Pageable pageable) {
        return ResponseEntity.ok(submissionService.getSubmissionList(CurrentUserHolder.getUserId(), pageable));
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<TeacherSubmissionResponse> getSubmission(@PathVariable Long submissionId) {
        return ResponseEntity.ok(submissionService.getSubmission(CurrentUserHolder.getUserId(), submissionId));
    }

    @PostMapping("/{submissionId}/comment")
    public ResponseEntity<Void> comment(@PathVariable Long submissionId, @RequestBody CommentRequest request) {
        submissionService.comment(CurrentUserHolder.getUserId(), submissionId, request.getContent());
        return ResponseEntity.ok().build();
    }
}

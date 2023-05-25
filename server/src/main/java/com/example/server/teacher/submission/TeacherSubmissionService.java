package com.example.server.teacher.submission;

import com.example.server.student.submission.Comment;
import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionRepository;
import com.example.server.student.submission.dto.SubmissionListResponse;
import com.example.server.teacher.submission.dto.TeacherSubmissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherSubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionListResponse getSubmissionList(Long teacherId, Pageable pageable) {
        Page<Submission> submissions = submissionRepository.findAllByTeacherId(teacherId, pageable);
        return new SubmissionListResponse(submissions);
    }

    public TeacherSubmissionResponse getSubmission(Long teacherId, Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("No Submission Exist"));
        if (!submission.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("Not Authorized");
        }
        return new TeacherSubmissionResponse(submission);
    }

    public void comment(Long teacherId, Long submissionId, String content) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("No Submission Exist"));
        if (!submission.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("Not Authorized");
        }
        submission.comment(new Comment(content));
    }
}

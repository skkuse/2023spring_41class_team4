package com.example.server.teacher.submission;

import com.example.server.exceptions.NoSubmissionException;
import com.example.server.exceptions.NotAuthorized;
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

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherSubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionListResponse getSubmissionList(Long teacherId, Optional<Long> studentId, Pageable pageable) {
        Page<Submission> submissions = studentId.isPresent()
                ? submissionRepository.findAllByTeacherIdAndStudentId(teacherId, studentId.get(), pageable)
                : submissionRepository.findAllByTeacherId(teacherId, pageable);
        return new SubmissionListResponse(submissions);
    }

    public TeacherSubmissionResponse getSubmission(Long teacherId, Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NoSubmissionException(submissionId));
        if (!submission.getTeacher().getId().equals(teacherId)) {
            throw new NotAuthorized("submission", submissionId);
        }
        return new TeacherSubmissionResponse(submission);
    }

    public void comment(Long teacherId, Long submissionId, String content) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotAuthorized("submission", submissionId));
        if (!submission.getTeacher().getId().equals(teacherId)) {
            throw new NotAuthorized("submission", submissionId);
        }
        submission.comment(new Comment(content));
    }
}

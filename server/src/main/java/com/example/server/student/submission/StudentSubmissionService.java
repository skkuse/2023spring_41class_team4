package com.example.server.student.submission;

import com.example.server.student.problem.Problem;
import com.example.server.student.problem.ProblemRepository;
import com.example.server.student.problem.SuggestedProblemRepository;
import com.example.server.student.submission.dto.SubmissionListResponse;
import com.example.server.student.submission.dto.SubmissionResponse;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentRepository;
import com.example.server.teacher.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentSubmissionService {

    private final StudentRepository studentRepository;
    private final ProblemRepository problemRepository;
    private final SuggestedProblemRepository suggestedProblemRepository;
    private final SubmissionRepository submissionRepository;
    private final FeedbackService feedbackService;

    public Submission submit(Long studentId, Long problemId, String language, String content) {
        if (!suggestedProblemRepository.existsByStudentIdAndProblemId(studentId, problemId)) {
            throw new RuntimeException("No Problem Exist");
        }

        final Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("No Student"));

        final Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("No Problem"));

        Submission submission = submissionRepository.save(new Submission(student, problem, language, content));
        feedbackService.requestFeedback(submission);
        return submission;
    }

    public SubmissionListResponse getSubmissionList(Long studentId, Pageable pageable) {
        return new SubmissionListResponse(submissionRepository.findAllByStudentId(studentId, pageable));
    }

    public SubmissionResponse getSubmission(Long studentId, Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("No Submission Exist"));

        if (!submission.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("Not Authorization");
        }
        return new SubmissionResponse(submission);
    }
}

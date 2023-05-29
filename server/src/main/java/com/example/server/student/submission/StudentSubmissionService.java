package com.example.server.student.submission;

import com.example.server.exceptions.NoProblemException;
import com.example.server.exceptions.NoSubmissionException;
import com.example.server.exceptions.NotAuthorized;
import com.example.server.student.problem.SuggestedProblem;
import com.example.server.student.problem.SuggestedProblemRepository;
import com.example.server.student.submission.dto.SubmissionListResponse;
import com.example.server.student.submission.dto.SubmissionResponse;
import com.example.server.teacher.feedback.Feedback;
import com.example.server.teacher.feedback.FeedbackService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentSubmissionService {

    private final SuggestedProblemRepository suggestedProblemRepository;
    private final SubmissionRepository submissionRepository;
    private final FeedbackService feedbackService;

    public Submission submit(Long studentId, Long problemId, String language, String content) {
        SuggestedProblem suggestedProblem = suggestedProblemRepository.findByStudentIdAndProblemId(studentId, problemId)
                .orElseThrow(() -> new NoProblemException(problemId));

        Submission submission = submissionRepository.save(new Submission(suggestedProblem.getStudent(), suggestedProblem.getProblem(), language, content));
        suggestedProblemRepository.delete(suggestedProblem);
        final CompletableFuture<Feedback> feedbackResult = feedbackService.requestFeedback(submission);

        feedbackResult.thenAccept(submission::feedback);

        return submission;
    }

    public SubmissionListResponse getSubmissionList(Long studentId, Pageable pageable) {
        return new SubmissionListResponse(submissionRepository.findAllByStudentId(studentId, pageable));
    }

    public SubmissionResponse getSubmission(Long studentId, Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NoSubmissionException(submissionId));

        if (!submission.getStudent().getId().equals(studentId)) {
            throw new NotAuthorized("submission", submissionId);
        }
        return new SubmissionResponse(submission);
    }
}

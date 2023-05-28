package com.example.server.teacher.feedback;

import com.example.server.student.submission.Submission;

public interface FeedbackService {

    Feedback requestFeedback(Submission submission);
}

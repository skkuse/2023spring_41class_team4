package com.example.server.teacher.feedback;

import com.example.server.student.submission.Submission;

import java.util.concurrent.CompletableFuture;

public interface FeedbackService {

    CompletableFuture<Feedback> requestFeedback(Submission submission);
}

package com.example.server.teacher.feedback;

import com.example.server.student.submission.Submission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SimpleFeedbackService implements FeedbackService {

    @Override
    public void requestFeedback(Submission submission) {
        submission.feedback(new Feedback("잘했어요", new Achievement()));
    }
}

package com.example.server.teacher.submission.dto;

import com.example.server.student.submission.Comment;
import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionStatus;
import com.example.server.student.user.Student;
import com.example.server.teacher.feedback.Achievement;
import com.example.server.teacher.feedback.Feedback;
import com.example.server.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class TeacherSubmissionResponse {

    private Long id;
    private Long problemId;
    private StudentResponse student;
    private SubmissionStatus status;
    private String language;
    private String content;
    private String createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FeedbackResponse feedback;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CommentResponse comment;

    public TeacherSubmissionResponse(Submission submission) {
        this.id = submission.getId();
        this.problemId = submission.getProblem().getId();
        this.status = submission.getStatus();
        this.language = submission.getLanguage();
        this.content = submission.getCode();
        this.createdAt = DateUtils.formattedDate(submission.getCreatedAt());
        this.student = new StudentResponse(submission.getStudent());
        submission.getFeedback().ifPresent(feedback -> this.feedback = new FeedbackResponse(feedback));
        submission.getComment().ifPresent(comment -> this.comment = new CommentResponse(comment));
    }

    @Getter
    private static class StudentResponse {

        private Long id;
        private String name;

        public StudentResponse(Student student) {
            this.id = student.getId();
            this.name = student.getName();
        }
    }

    @Getter
    private static class FeedbackResponse {

        private String overview;
        private Achievement achievement;

        public FeedbackResponse(Feedback feedback) {
            this.overview = feedback.getOverview();
            this.achievement = feedback.getAchievement();
        }
    }

    @Getter
    private static class CommentResponse {

        private String content;
        private String createdAt;

        public CommentResponse(Comment comment) {
            this.content = comment.getContent();
            this.createdAt = DateUtils.formattedDate(comment.getCreatedAt());
        }
    }
}

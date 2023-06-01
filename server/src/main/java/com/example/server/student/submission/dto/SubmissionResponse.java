package com.example.server.student.submission.dto;

import com.example.server.student.submission.Comment;
import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionStatus;
import com.example.server.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class SubmissionResponse {

    private Long id;
    private Long problemId;
    private SubmissionStatus status;
    private String language;
    private String content;
    private String createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CommentResponse comment;

    public SubmissionResponse(Submission submission) {
        this.id = submission.getId();
        this.problemId = submission.getProblem().getId();
        this.status = submission.getStatus();
        this.language = submission.getLanguage();
        this.content = submission.getCode();
        this.createdAt = DateUtils.formattedDate(submission.getCreatedAt());
        submission.getComment().ifPresent(comment -> this.comment = new CommentResponse(comment));
    }

    @Getter
    public static class CommentResponse {

        private String content;
        private String createdAt;

        public CommentResponse(Comment comment) {
            this.content = comment.getContent();
            this.createdAt = DateUtils.formattedDate(comment.getCreatedAt());
        }
    }
}

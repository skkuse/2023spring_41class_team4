package com.example.server.student.submission;

import com.example.server.student.problem.Problem;
import com.example.server.student.problem.SuggestedProblem;
import com.example.server.student.user.Student;
import com.example.server.teacher.feedback.Feedback;
import com.example.server.teacher.user.Teacher;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Submission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggested_problem_id")
    private SuggestedProblem suggestedProblem;

    private SubmissionStatus status;

    private String language;

    private String code;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Submission(SuggestedProblem suggestedProblem, String language, String code) {
        this.suggestedProblem = suggestedProblem;
        this.status = SubmissionStatus.SOLVE;
        this.language = language;
        this.code = code;
    }

    public Student getStudent() {
        return suggestedProblem.getStudent();
    }

    public Teacher getTeacher() {
        return suggestedProblem.getStudent().getTeacher();
    }

    public Problem getProblem() {
        return suggestedProblem.getProblem();
    }

    public void feedback(Feedback feedback) {
        this.feedback = feedback;
        this.status = SubmissionStatus.FEEDBACK;
    }

    public void comment(Comment comment) {
        this.comment = comment;
        this.status = SubmissionStatus.COMMENT;
    }

    public Optional<Feedback> getFeedback() {
        return Optional.ofNullable(feedback);
    }

    public Optional<Comment> getComment() {
        return Optional.ofNullable(comment);
    }
}

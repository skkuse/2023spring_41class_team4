package com.example.server.student.submission;

import com.example.server.student.problem.Problem;
import com.example.server.student.user.Student;
import com.example.server.teacher.feedback.Feedback;
import com.example.server.teacher.user.Teacher;
import com.example.server.utils.TimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Submission extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private SubmissionStatus status;

    private String language;

    private String code;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Submission(Student student, Problem problem, String language, String code) {
        this.student = student;
        this.problem = problem;
        this.status = SubmissionStatus.SOLVE;
        this.language = language;
        this.code = code;
    }

    public Teacher getTeacher() {
        return this.student.getTeacher();
    }

    public void feedback(Feedback feedback) {
        this.feedback = feedback;
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

package com.example.server.student.submission.dto;

import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionStatus;
import com.example.server.student.user.Student;
import com.example.server.utils.DateUtils;
import com.example.server.utils.PageInfo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class SubmissionListResponse {

    private List<SubmissionElement> submissions;
    private PageInfo pageInfo;

    public SubmissionListResponse(Page<Submission> submissions) {
        this.submissions = submissions.map(SubmissionElement::new).toList();
        this.pageInfo = new PageInfo(submissions);
    }

    @Getter
    public static class SubmissionElement {
        private Long id;
        private Long problemId;
        private StudentResponse student;
        private SubmissionStatus status;
        private String createdAt;

        public SubmissionElement(Submission submission) {
            this.id = submission.getId();
            this.problemId = submission.getProblem().getId();
            this.student = new StudentResponse(submission.getStudent());
            this.status = submission.getStatus();
            this.createdAt = DateUtils.formattedDate(submission.getCreatedAt());
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
    }
}

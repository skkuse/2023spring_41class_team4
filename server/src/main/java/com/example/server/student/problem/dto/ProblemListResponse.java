package com.example.server.student.problem.dto;

import com.example.server.student.problem.Problem;
import com.example.server.student.problem.SuggestedProblem;
import com.example.server.utils.PageInfo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ProblemListResponse {

    private List<ProblemElement> problems;
    private PageInfo pageInfo;

    public ProblemListResponse(Page<SuggestedProblem> problems) {
        this.problems = problems.map(problem -> new ProblemElement(problem.getProblem())).toList();
        this.pageInfo = new PageInfo(problems);
    }

    @Getter
    private static class ProblemElement {

        private Long id;
        private String title;
        private String link;

        public ProblemElement(Problem problem) {
            this.id = problem.getId();
            this.title = problem.getTitle();
            this.link = problem.getContent();
        }
    }
}

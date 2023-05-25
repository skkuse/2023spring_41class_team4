package com.example.server.student.problem;

import com.example.server.student.problem.dto.ProblemListResponse;
import com.example.server.student.problem.dto.ProblemResponse;
import com.example.server.student.problem.dto.SolvedacResponse;
import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionRepository;
import com.example.server.student.user.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {

    public static final String BOJ_PROBLEM_URL = "https://www.acmicpc.net/problem/";

    private final SuggestedProblemRepository suggestedProblemRepository;
    private final ProblemLoader problemLoader;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;

    public ProblemListResponse getProblemList(Long studentId, Pageable pageable) {
        Page<SuggestedProblem> problems = suggestedProblemRepository.findAllByStudentId(studentId, pageable);
        return new ProblemListResponse(problems);
    }

    public ProblemResponse getProblem(Long studentId, Long problemId) {
        SuggestedProblem problem = suggestedProblemRepository.findByStudentIdAndProblemId(studentId, problemId)
                .orElseThrow(() -> new RuntimeException("No Problem Exist"));
        return new ProblemResponse(problem.getProblem());
    }

    public void load(Student student, String bojId) {
        final SolvedacResponse solvedProblems = problemLoader.load(bojId);

        assert solvedProblems != null;

        solvedProblems.getItems()
                .forEach(item -> {
                    final Problem problem = new Problem(item.getProblemId(), item.getTitleKo(),
                            changeToUrl(item.getProblemId()));
                    if (!problemRepository.existsByProblemNumber(item.getProblemId())) {
                        problemRepository.save(problem);
                    }

                    submissionRepository.save(new Submission(student, problem, "", ""));
                });
    }

    private String changeToUrl(int problemId) {
        return BOJ_PROBLEM_URL + problemId;
    }
}

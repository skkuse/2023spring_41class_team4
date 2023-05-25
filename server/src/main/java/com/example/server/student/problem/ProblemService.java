package com.example.server.student.problem;

import com.example.server.student.problem.dto.ProblemListResponse;
import com.example.server.student.problem.dto.ProblemResponse;
import com.example.server.student.problem.dto.SolvedacProblemResponse;
import com.example.server.student.problem.dto.SolvedacResponse;
import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionRepository;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {

    public static final String BOJ_PROBLEM_URL = "https://www.acmicpc.net/problem/";

    private final SuggestedProblemRepository suggestedProblemRepository;
    private final ProblemRecommender problemRecommender;
    private final StudentRepository studentRepository;
    private final ProblemLoader problemLoader;
    private final ProblemFetcher problemFetcher;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;


    public ProblemListResponse getProblemList(Long studentId, Pageable pageable) {
        final Page<SuggestedProblem> suggestedProblems = suggestedProblemRepository.findAllByStudentId(studentId, pageable);
        if (suggestedProblems.isEmpty()) {
            final Page<Submission> submissions = submissionRepository.findAllByStudentId(studentId, pageable);
            final String solvedProblems = submissions.stream()
                    .map(submission -> String.valueOf(submission.getProblem().getProblemNumber()))
                    .collect(Collectors.joining(" "));

            final Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("No Student Exist"));
            final List<Integer> recommends = problemRecommender.recommend(solvedProblems);
            recommends.forEach(recommend -> {
                final SolvedacProblemResponse response = problemFetcher.fetch(String.valueOf(recommend));
                final Problem problem = new Problem(recommend, response.getTitleKo(), changeToUrl(recommend));
                if (!problemRepository.existsByProblemNumber(recommend)) {
                    problemRepository.save(problem);
                }
                suggestedProblemRepository.save(new SuggestedProblem(student, problem));
            });

            return new ProblemListResponse(suggestedProblemRepository.findAllByStudentId(studentId, pageable));
        }
        return new ProblemListResponse(suggestedProblems);
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

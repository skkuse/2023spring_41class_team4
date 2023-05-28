package com.example.server.student.problem;

import com.example.server.exceptions.NoProblemException;
import com.example.server.exceptions.NoStudentException;
import com.example.server.student.problem.dto.ProblemListResponse;
import com.example.server.student.problem.dto.ProblemResponse;
import com.example.server.student.problem.dto.SolvedacProblemResponse;
import com.example.server.student.problem.dto.SolvedacResponse;
import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionRepository;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        final Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoStudentException(studentId));

        if (suggestedProblems.isEmpty()) {
            final Page<Submission> submissions = submissionRepository.findAllByStudentId(studentId, pageable);
            final SolvedacResponse problemsFromSolvedac = problemLoader.load(student.getBojId());

            Set<String> problemSet = new HashSet<>();
            problemSet.addAll(submissions.getContent().stream().map(submission ->
                    String.valueOf(submission.getProblem().getId())
            ).toList());
            problemSet.addAll(problemsFromSolvedac.getItems().stream().map(item ->
                    String.valueOf(item.getProblemId())
            ).toList());

            String problemsString = String.join(" ", problemSet);

            final List<Long> recommends = problemRecommender.recommend(problemsString);
            recommends.forEach(recommend -> {
                Problem problem = problemRepository.findById(recommend).orElseGet(() -> {
                            SolvedacProblemResponse response = problemFetcher.fetch(String.valueOf(recommend));
                            return problemRepository.save(new Problem(recommend, response.getTitleKo(), changeToUrl(recommend)));
                        }
                );
                suggestedProblemRepository.save(new SuggestedProblem(student, problem));
            });

            return new ProblemListResponse(suggestedProblemRepository.findAllByStudentId(studentId, pageable));
        }
        return new ProblemListResponse(suggestedProblems);
    }

    public ProblemResponse getProblem(Long studentId, Long problemId) {
        SuggestedProblem problem = suggestedProblemRepository.findByStudentIdAndProblemId(studentId, problemId)
                .orElseThrow(() -> new NoProblemException(problemId));
        return new ProblemResponse(problem.getProblem());
    }

    private String changeToUrl(Long problemId) {
        return BOJ_PROBLEM_URL + problemId;
    }
}

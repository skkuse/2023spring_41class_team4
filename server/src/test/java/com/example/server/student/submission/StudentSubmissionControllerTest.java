package com.example.server.student.submission;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.student.problem.Problem;
import com.example.server.student.problem.ProblemRepository;
import com.example.server.student.problem.SuggestedProblem;
import com.example.server.student.problem.SuggestedProblemRepository;
import com.example.server.student.problem.dto.ProblemSubmitRequest;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentRepository;
import com.example.server.teacher.feedback.Achievement;
import com.example.server.teacher.feedback.Feedback;
import com.example.server.teacher.feedback.FeedbackService;
import com.example.server.teacher.user.Teacher;
import com.example.server.teacher.user.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class StudentSubmissionControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TokenResolver tokenResolver;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SuggestedProblemRepository suggestedProblemRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @MockBean(name = "simpleFeedbackService")
    private FeedbackService feedbackService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUpMockMvc() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("학생이 제출한 답안을 반환한다.")
    void getSubmissionList() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        Student other = studentRepository.save(new Student("other@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        Problem problem1 = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        Problem problem2 = problemRepository.save(new Problem(1001L, "A-B", "A-B"));

        Submission submission1 = submissionRepository.save(new Submission(student, problem1, "python3", "a+b"));
        Submission submission2 = submissionRepository.save(new Submission(student, problem2, "python3", "a-b"));
        submissionRepository.save(new Submission(other, problem1, "python3", "a+b"));

        mvc.perform(get("/submissions")
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.submissions[0].id").value(submission1.getId()))
                .andExpect(jsonPath("$.submissions[1].id").value(submission2.getId()))
                .andExpect(jsonPath("$.submissions[2].id").doesNotExist());
    }

    @Test
    @DisplayName("답안 id에 해당하는 답안을 반환한다.")
    void getSubmission() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        Problem problem = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        Submission submission = submissionRepository.save(new Submission(student, problem, "python3", "a+b"));

        mvc.perform(get("/submissions/{submissionId}", submission.getId())
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(submission.getId()))
                .andExpect(jsonPath("$.problemId").value(submission.getProblem().getId()));
    }

    @Test
    @DisplayName("답안이 없는 경우 404 오류를 발생한다.")
    void noSubmission() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        mvc.perform(get("/submissions/{submissionId}", 1L)
                        .header("X-Auth-Token", token))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("다른 학생의 답안을 조회하는 경우 403 오류가 발생한다.")
    void othersSubmission() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        Student other = studentRepository.save(new Student("other@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        Problem problem = problemRepository.save(new Problem(1000L, "A+B", "A+B"));

        submissionRepository.save(new Submission(student, problem, "python3", "a+b"));
        Submission submission = submissionRepository.save(new Submission(other, problem, "python3", "a-b"));

        mvc.perform(get("/submissions/{submissionId}", submission.getId())
                        .header("X-Auth-Token", token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("답안을 제출하면 피드백이 작성되며, 추천 문제가 사라진다.")
    void submit() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        Problem problem = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        SuggestedProblem suggestedProblem = suggestedProblemRepository.save(new SuggestedProblem(student, problem));

        Feedback feedback = new Feedback("Feedback", new Achievement());
        given(feedbackService.requestFeedback(any())).willReturn(CompletableFuture.completedFuture(feedback));

        String content = objectMapper.writeValueAsString(new ProblemSubmitRequest("python3", "code"));
        mvc.perform(post("/problems/{problemId}/submit", problem.getId())
                        .header("X-Auth-Token", token)
                        .header("content-type", "application/json")
                        .content(content))
                .andExpect(status().isOk());

        assertThat(suggestedProblemRepository.findById(suggestedProblem.getId())).isEmpty();
        assertThat(submissionRepository.findAllByStudentId(student.getId(), PageRequest.of(0, 100))
                .map(Submission::getFeedback).getContent())
                .contains(Optional.of(feedback));
    }
}
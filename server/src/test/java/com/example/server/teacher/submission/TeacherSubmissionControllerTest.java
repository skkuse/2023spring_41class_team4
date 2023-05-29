package com.example.server.teacher.submission;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.student.problem.Problem;
import com.example.server.student.problem.ProblemRepository;
import com.example.server.student.submission.Submission;
import com.example.server.student.submission.SubmissionRepository;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentRepository;
import com.example.server.teacher.submission.dto.CommentRequest;
import com.example.server.teacher.user.Teacher;
import com.example.server.teacher.user.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class TeacherSubmissionControllerTest {

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
    private SubmissionRepository submissionRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUpMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("학생들이 제출한 답안 목록을 조회한다.")
    void getSubmissionList() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        Problem problem1 = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        Problem problem2 = problemRepository.save(new Problem(1001L, "A-B", "A-B"));

        Submission submission1 = submissionRepository.save(new Submission(student, problem1, "python3", "a+b"));
        Submission submission2 = submissionRepository.save(new Submission(student, problem2, "python3", "a-b"));

        mvc.perform(get("/teacher/submissions")
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.submissions[0].id").value(submission1.getId()))
                .andExpect(jsonPath("$.submissions[1].id").value(submission2.getId()));
    }

    @Test
    @DisplayName("학생들이 제출한 답안 목록을 조회한다.")
    void getSubmissionsByStudent() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        Problem problem1 = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        Problem problem2 = problemRepository.save(new Problem(1001L, "A-B", "A-B"));

        Submission submission1 = submissionRepository.save(new Submission(student, problem1, "python3", "a+b"));
        Submission submission2 = submissionRepository.save(new Submission(student, problem2, "python3", "a-b"));

        mvc.perform(get("/teacher/submissions/students/{studentId}", student.getId())
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.submissions[0].id").value(submission1.getId()))
                .andExpect(jsonPath("$.submissions[1].id").value(submission2.getId()));
    }

    @Test
    @DisplayName("학생이 제출한 답안을 조회한다.")
    void getSubmission() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        Problem problem = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        Submission submission = submissionRepository.save(new Submission(student, problem, "python3", "a+b"));

        mvc.perform(get("/teacher/submissions/{submissionId}", submission.getId())
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("다른 강사의 학생이 제출한 답안을 조회하는 경우 403 오류가 발생한다.")
    void getOthersSubmission() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Teacher other = teacherRepository.save(new Teacher("other@gmail.com", "password", "teacher", "22222222"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        Student othersStudent = studentRepository.save(new Student("otherStudent@gmail.com", "password", "student", "student", other));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        Problem problem = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        Submission submission = submissionRepository.save(new Submission(othersStudent, problem, "python3", "a+b"));

        mvc.perform(get("/teacher/submissions/{submissionId}", submission.getId())
                        .header("X-Auth-Token", token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("학생이 작성한 답안에 코멘트를 작성한다.")
    void comment() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        Problem problem = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        Submission submission = submissionRepository.save(new Submission(student, problem, "python3", "a+b"));

        CommentRequest request = new CommentRequest("comment");
        String content = objectMapper.writeValueAsString(request);
        mvc.perform(post("/teacher/submissions/{submissionId}/comment", submission.getId())
                        .header("content-type", "application/json")
                        .header("X-Auth-Token", token)
                        .content(content))
                .andExpect(status().isOk());

        assertThat(submission.getComment()).isPresent();
    }
}
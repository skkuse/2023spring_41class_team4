package com.example.server.student.problem;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.student.problem.dto.SolvedacProblemResponse;
import com.example.server.student.problem.dto.SolvedacResponse;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentRepository;
import com.example.server.teacher.user.Teacher;
import com.example.server.teacher.user.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class ProblemControllerTest {

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

    @MockBean(name = "problemRecommenderImpl")
    private ProblemRecommender problemRecommender;

    @MockBean(name = "problemFetcherImpl")
    private ProblemFetcher problemFetcher;

    @MockBean(name = "problemLoaderImpl")
    private ProblemLoader problemLoader;

    @BeforeEach
    public void setUpMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("추천 문제가 있는 경우 추천 문제를 반환한다.")
    void getProblems() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        Problem problem = problemRepository.save(new Problem(1000L, "A+B", "A+B"));
        suggestedProblemRepository.save(new SuggestedProblem(student, problem));

        mvc.perform(get("/problems")
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problems[0].id").value(1000L))
                .andExpect(jsonPath("$.problems[1]").doesNotExist());
    }

    @Test
    @DisplayName("추천 문제가 없는 경우 새로운 문제를 추천한다.")
    void recommendProblems() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        given(problemRecommender.recommend(any()))
                .willReturn(List.of(1000L, 1001L, 1002L, 1003L, 1004L));

        given(problemFetcher.fetch(any()))
                .willAnswer(parameters ->
                        new SolvedacProblemResponse("problem" + parameters.getArgument(0)));

        given(problemLoader.load(student.getBojId()))
                .willReturn(new SolvedacResponse(0, List.of()));

        mvc.perform(get("/problems")
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problems[0].id").value(1000L))
                .andExpect(jsonPath("$.problems[1].id").value(1001L))
                .andExpect(jsonPath("$.problems[2].id").value(1002L))
                .andExpect(jsonPath("$.problems[3].id").value(1003L))
                .andExpect(jsonPath("$.problems[4].id").value(1004L));
    }
}
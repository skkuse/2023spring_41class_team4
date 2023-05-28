package com.example.server.student.problem;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.student.problem.dto.SolvedacProblemResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class ProblemLoadTest {

    private MockMvc mock;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TokenResolver tokenResolver;

    @MockBean(name = "problemRecommenderImpl")
    private ProblemRecommender problemRecommender;

    @MockBean(name = "problemFetcherImpl")
    private ProblemFetcher problemFetcher;

    @BeforeEach
    public void setUpMockMvc() {
        this.mock = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("백준 ID에 해당하는 계정이 존재하지 않는 경우 401 오류가 발생한다.")
    void noBojAccount() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        given(problemRecommender.recommend(any()))
                .willReturn(List.of(1000L, 1001L, 1002L, 1003L, 1004L));

        given(problemFetcher.fetch(any()))
                .willAnswer(parameters ->
                        new SolvedacProblemResponse("problem" + parameters.getArgument(0)));

        mock.perform(get("/problems")
                        .header("X-Auth-Token", token))
                .andExpect(status().isUnauthorized());
    }
}
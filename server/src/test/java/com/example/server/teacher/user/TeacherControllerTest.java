package com.example.server.teacher.user;

import com.example.server.teacher.user.dto.TeacherSignUpRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class TeacherControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TeacherRepository teacherRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUpMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("중복된 이메일이 입력되면 409 오류가 발생한다.")
    void signUp() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));

        TeacherSignUpRequest request = new TeacherSignUpRequest(teacher.getEmail(), teacher.getPassword(), teacher.getName());
        String content = objectMapper.writeValueAsString(request);
        mvc.perform(post("/teacher/users")
                        .content(content)
                        .header("content-type", "application/json"))
                .andExpect(status().isConflict());
    }
}
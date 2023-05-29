package com.example.server.auth;

import com.example.server.exceptions.ExceptionResponseAdvice;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentController;
import com.example.server.student.user.StudentRepository;
import com.example.server.teacher.user.Teacher;
import com.example.server.teacher.user.TeacherController;
import com.example.server.teacher.user.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class TokenInterceptorTest {

    private MockMvc mvc;

    @Autowired
    StudentController studentController;
    @Autowired
    TeacherController teacherController;

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TokenResolver tokenResolver;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(studentController, teacherController)
                .addInterceptors(new TokenInterceptor(tokenResolver))
                .setControllerAdvice(ExceptionResponseAdvice.class)
                .build();
    }

    @Test
    @DisplayName("학생 도메인에 학생이 접근하는 경우 성공한다.")
    void studentToken() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        mvc.perform(get("/me")
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("강사 도메인에 강사가 접근하는 경우 성공한다.")
    void teacherToken() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        mvc.perform(get("/teacher/me")
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("토큰을 입력하지 않은 경우 401 오류가 발생한다.")
    void noToken() throws Exception {
        teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));

        mvc.perform(get("/teacher/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("학생이 강사의 도메인에 접근하는 경우 403 오류가 발생한다.")
    void accessToTeacher() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(student));

        mvc.perform(get("/teacher/me")
                        .header("X-Auth-Token", token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("강사가 학생의 도메인에 접근하는 경우 403 오류가 발생한다.")
    void accessToStudent() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        mvc.perform(get("/me")
                        .header("X-Auth-Token", token))
                .andExpect(status().isForbidden());
    }
}
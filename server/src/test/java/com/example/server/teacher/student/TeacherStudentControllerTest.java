package com.example.server.teacher.student;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.student.user.Student;
import com.example.server.student.user.StudentRepository;
import com.example.server.teacher.user.Teacher;
import com.example.server.teacher.user.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class TeacherStudentControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TokenResolver tokenResolver;

    @BeforeEach
    public void setUpMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("등록된 학생 목록을 반환한다.")
    void getStudentList() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Teacher other = teacherRepository.save(new Teacher("other@gmail.com", "password", "teacher", "22222222"));
        Student student1 = studentRepository.save(new Student("student1@gmail.com", "password", "student", "student", teacher));
        Student student2 = studentRepository.save(new Student("student2@gmail.com", "password", "student", "student", teacher));
        Student student3 = studentRepository.save(new Student("student3@gmail.com", "password", "student", "student", teacher));
        Student otherStudent1 = studentRepository.save(new Student("student4@gmail.com", "password", "student", "student", other));
        Student otherStudent2 = studentRepository.save(new Student("student5@gmail.com", "password", "student", "student", other));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        mvc.perform(get("/teacher/students")
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students[0].id").value(student1.getId()))
                .andExpect(jsonPath("$.students[1].id").value(student2.getId()))
                .andExpect(jsonPath("$.students[2].id").value(student3.getId()))
                .andExpect(jsonPath("$.students[3].id").doesNotExist());
    }

    @Test
    @DisplayName("학생 정보를 반환한다.")
    void getStudent() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        mvc.perform(get("/teacher/students/{studentId}", student.getId())
                        .header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }

    @Test
    @DisplayName("강사에게 등록되지 않은 학생에 접근하는 경우 403 오류가 발생한다.")
    void getOthersStudent() throws Exception {
        Teacher teacher = teacherRepository.save(new Teacher("teacher@gmail.com", "password", "teacher", "11111111"));
        Teacher other = teacherRepository.save(new Teacher("other@gmail.com", "password", "teacher", "22222222"));
        Student student = studentRepository.save(new Student("student@gmail.com", "password", "student", "student", teacher));
        Student othersStudent = studentRepository.save(new Student("otherStudent@gmail.com", "password", "student", "student", other));
        String token = tokenResolver.encode(new CurrentUser(teacher));

        mvc.perform(get("/teacher/students/{studentId}", othersStudent.getId())
                        .header("X-Auth-Token", token))
                .andExpect(status().isForbidden());
    }
}
package com.example.server.student.user;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.student.user.dto.StudentInfoResponse;
import com.example.server.student.user.dto.StudentLoginResponse;
import com.example.server.teacher.student.dto.StudentListResponse;
import com.example.server.teacher.student.dto.StudentResponse;
import com.example.server.teacher.user.Teacher;
import com.example.server.teacher.user.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TokenResolver tokenResolver;

    public Student signUp(String email, String password, String name, String githubAccount, String teacherCode) {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherCode)
                .orElseThrow(() -> new RuntimeException("No Teacher Exist"));
        return studentRepository.save(new Student(email, password, name, githubAccount, teacher));
    }

    public StudentLoginResponse login(String email, String password) {
        Student student = studentRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Student Not Exist"));
        return new StudentLoginResponse(tokenResolver.encode(new CurrentUser(student)));
    }

    public StudentInfoResponse findMe(Long userId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student Not Exist"));
        return new StudentInfoResponse(student);
    }

    public StudentListResponse getStudentList(Long teacherId, Pageable pageable) {
        Page<Student> students = studentRepository.findAllByTeacherId(teacherId, pageable);
        return new StudentListResponse(students);
    }

    public StudentResponse getStudent(Long teacherId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("No Student Exist"));
        if (!student.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("No Authorization");
        }
        return new StudentResponse(student);
    }
}

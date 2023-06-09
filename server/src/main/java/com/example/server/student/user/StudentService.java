package com.example.server.student.user;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.exceptions.DuplicateStudentException;
import com.example.server.exceptions.NoStudentException;
import com.example.server.exceptions.NoTeacherException;
import com.example.server.exceptions.NotAuthorized;
import com.example.server.student.user.dto.StudentInfoResponse;
import com.example.server.student.user.dto.StudentLoginResponse;
import com.example.server.teacher.student.dto.StudentListResponse;
import com.example.server.teacher.student.dto.StudentResponse;
import com.example.server.teacher.user.Teacher;
import com.example.server.teacher.user.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Student signUp(String email, String password, String name, String bojId, String teacherCode) {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherCode)
                .orElseThrow(() -> new NoTeacherException(teacherCode));
        try {
            return studentRepository.save(new Student(email, password, name, bojId, teacher));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateStudentException(email);
        }
    }

    public StudentLoginResponse login(String email, String password) {
        Student student = studentRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new NoStudentException(email));
        return new StudentLoginResponse(tokenResolver.encode(new CurrentUser(student)));
    }

    public StudentInfoResponse findMe(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoStudentException(studentId));
        return new StudentInfoResponse(student);
    }

    public StudentListResponse getStudentList(Long teacherId, Pageable pageable) {
        Page<Student> students = studentRepository.findAllByTeacherId(teacherId, pageable);
        return new StudentListResponse(students);
    }

    public StudentResponse getStudent(Long teacherId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoStudentException(studentId));
        if (!student.getTeacher().getId().equals(teacherId)) {
            throw new NotAuthorized("student", studentId);
        }
        return new StudentResponse(student);
    }
}

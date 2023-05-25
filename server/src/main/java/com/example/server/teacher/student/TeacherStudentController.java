package com.example.server.teacher.student;

import com.example.server.auth.CurrentUserHolder;
import com.example.server.student.user.StudentService;
import com.example.server.teacher.student.dto.StudentListResponse;
import com.example.server.teacher.student.dto.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher/students")
public class TeacherStudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<StudentListResponse> getStudentList(Pageable pageable) {
        return ResponseEntity.ok(studentService.getStudentList(CurrentUserHolder.getUserId(), pageable));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudent(CurrentUserHolder.getUserId(), studentId));
    }
}

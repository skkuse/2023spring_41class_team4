package com.example.server.student.user;

import com.example.server.auth.CurrentUserHolder;
import com.example.server.student.user.dto.StudentInfoResponse;
import com.example.server.student.user.dto.StudentLoginRequest;
import com.example.server.student.user.dto.StudentLoginResponse;
import com.example.server.student.user.dto.StudentSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/users")
    public ResponseEntity<Void> signUp(@RequestBody StudentSignUpRequest request) {
        studentService.signUp(request.getEmail(), request.getPassword(), request.getName(), request.getGithubAccount(), request.getTeacherCode());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/login")
    public ResponseEntity<StudentLoginResponse> login(@RequestBody StudentLoginRequest request) {
        return ResponseEntity.ok(studentService.login(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/me")
    public ResponseEntity<StudentInfoResponse> findMe() {
        return ResponseEntity.ok(studentService.findMe(CurrentUserHolder.getUserId()));
    }
}

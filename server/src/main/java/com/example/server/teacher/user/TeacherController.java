package com.example.server.teacher.user;

import com.example.server.auth.CurrentUserHolder;
import com.example.server.teacher.user.dto.TeacherInfoResponse;
import com.example.server.teacher.user.dto.TeacherLoginRequest;
import com.example.server.teacher.user.dto.TeacherLoginResponse;
import com.example.server.teacher.user.dto.TeacherSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/users")
    public ResponseEntity<Void> signUp(@RequestBody TeacherSignUpRequest request) {
        teacherService.signUp(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/login")
    public ResponseEntity<TeacherLoginResponse> login(@RequestBody TeacherLoginRequest request) {
        return ResponseEntity.ok(teacherService.login(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/me")
    public ResponseEntity<TeacherInfoResponse> findMe() {
        return ResponseEntity.ok(teacherService.findMe(CurrentUserHolder.getUserId()));
    }
}

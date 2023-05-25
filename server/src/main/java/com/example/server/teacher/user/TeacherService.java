package com.example.server.teacher.user;

import com.example.server.auth.CurrentUser;
import com.example.server.auth.TokenResolver;
import com.example.server.teacher.user.dto.TeacherInfoResponse;
import com.example.server.teacher.user.dto.TeacherLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TokenResolver tokenResolver;

    public void signUp(String email, String password, String name) {
        teacherRepository.save(new Teacher(email, password, name, createTeacherCode()));
    }

    private String createTeacherCode() {
        String teacherCode = UUID.randomUUID().toString().substring(0, 8);
        while (teacherRepository.existsByTeacherCode(teacherCode)) {
            teacherCode = UUID.randomUUID().toString().substring(0, 8);
        }
        return teacherCode;
    }

    public TeacherLoginResponse login(String email, String password) {
        Teacher teacher = teacherRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("No Teacher Exist"));
        return new TeacherLoginResponse(tokenResolver.encode(new CurrentUser(teacher)));
    }


    public TeacherInfoResponse findMe(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("No Teacher Exist"));
        return new TeacherInfoResponse(teacher);
    }
}

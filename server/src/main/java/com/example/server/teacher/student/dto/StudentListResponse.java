package com.example.server.teacher.student.dto;

import com.example.server.student.user.Student;
import com.example.server.utils.PageInfo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class StudentListResponse {

    private List<StudentElement> students;
    private PageInfo pageInfo;

    public StudentListResponse(Page<Student> students) {
        this.students = students.map(StudentElement::new).toList();
        this.pageInfo = new PageInfo(students);
    }

    @Getter
    private static class StudentElement {
        private Long id;
        private String name;

        public StudentElement(Student student) {
            this.id = student.getId();
            this.name = student.getName();
        }
    }
}

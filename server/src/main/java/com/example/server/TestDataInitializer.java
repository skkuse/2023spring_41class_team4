package com.example.server;

import com.example.server.student.problem.Problem;
import com.example.server.student.problem.SuggestedProblem;
import com.example.server.student.submission.Comment;
import com.example.server.student.submission.Submission;
import com.example.server.student.user.Student;
import com.example.server.teacher.feedback.Achievement;
import com.example.server.teacher.feedback.Feedback;
import com.example.server.teacher.user.Teacher;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final TestDataCreator creator;

    @PostConstruct
    public void setup() {
        creator.setup();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class TestDataCreator {

        private final EntityManager em;

        public void setup() {
            Teacher teacher1 = new Teacher("teacher1@email.com", "password", "teacher1", "11111111");
            Teacher teacher2 = new Teacher("teacher2@email.com", "password", "teacher2", "22222222");
            em.persist(teacher1);
            em.persist(teacher2);

            Student student1 = new Student("student1@email.com", "password", "student1", "student1", teacher1);
            Student student2 = new Student("student2@email.com", "password", "student2", "student2", teacher1);
            Student student3 = new Student("student3@email.com", "password", "student3", "student3", teacher2);
            Student student4 = new Student("student4@email.com", "password", "student4", "student4", teacher2);
            em.persist(student1);
            em.persist(student2);
            em.persist(student3);
            em.persist(student4);

            Problem problem1 = new Problem(1000, "A+B", "두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.");
            Problem problem2 = new Problem(1000, "A-B", "두 정수 A와 B를 입력받은 다음, A-B를 출력하는 프로그램을 작성하시오.");
            em.persist(problem1);
            em.persist(problem2);

            SuggestedProblem suggestedProblem1 = new SuggestedProblem(student2, problem1);
            SuggestedProblem suggestedProblem2 = new SuggestedProblem(student2, problem2);
            SuggestedProblem suggestedProblem3 = new SuggestedProblem(student3, problem2);
            SuggestedProblem suggestedProblem4 = new SuggestedProblem(student4, problem1);
            em.persist(suggestedProblem1);
            em.persist(suggestedProblem2);
            em.persist(suggestedProblem3);
            em.persist(suggestedProblem4);

            Submission submission1 = new Submission(student1, problem1, "python3", "print(A + B)");
            Submission submission2 = new Submission(student1, problem2, "python3", "print(A - B)");
            em.persist(submission1);
            em.persist(submission2);

            Feedback feedback1 = new Feedback("잘했어요", new Achievement(90, 80, 70, 60, 50, 40));
            Feedback feedback2 = new Feedback("잘했어요", new Achievement(90, 60, 70, 60, 70, 40));
            submission1.feedback(feedback1);
            submission2.feedback(feedback2);

            Comment comment = new Comment("잘했어요");
            submission1.comment(comment);
        }
    }
}

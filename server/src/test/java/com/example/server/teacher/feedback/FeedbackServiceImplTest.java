package com.example.server.teacher.feedback;

import static org.junit.jupiter.api.Assertions.*;

import com.example.server.student.problem.Problem;
import com.example.server.student.submission.Submission;
import com.example.server.student.user.Student;
import com.example.server.teacher.user.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedbackServiceImplTest {

    @Autowired
    private FeedbackService feedbackService;

    @Test
    void FeedbackServiceImplTest() {
        // given
        final Teacher teacher = new Teacher("test", "test", "test", "test");
        final Student student = new Student("test", "test", "test", "test", teacher);
        final Problem problem = new Problem(1L, "test", "test");
        final Submission submission = new Submission(student, problem, "C", "import requests\n"
                + "import json\n"
                + "import re\n"
                + "import subprocess\n"
                + "import time\n"
                + "\n"
                + "\n"
                + "def send_api():\n"
                + "    API_HOST = \"https://www.swmaestro.org\"\n"
                + "    url = API_HOST + \"/sw/mypage/mentoLec/list.do?menuNo=200046\"\n"
                + "    headers = {\n"
                + "        \"User-Agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36\",\n"
                + "        \"Content-Type\": \"application/json\",\n"
                + "        \"Accept\": \"*/*\",\n"
                + "        \"Accept-Encoding\": \"gzip, deflate, br\",\n"
                + "        \"Connection\": \"keep-alive\",\n"
                + "        \"Cookie\": \"JSESSIONID=AA269D7DAE0B68BCA9996AF5F2BF2C82\"\n"
                + "    }\n"
                + "\n"
                + "    response = requests.get(url, headers=headers)\n"
                + "\n"
                + "    handle_response(response.text)\n"
                + "\n"
                + "\n"
                + "\n");

        feedbackService.requestFeedback(submission);

        // when

        // then
        System.out.println("asdf");
    }
}
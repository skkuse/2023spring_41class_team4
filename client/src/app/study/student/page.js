"use client";
import Link from "next/link";
import axios from "axios";
import { useEffect, useState } from "react";
import "./page.css";

export default function Problems() {
  let id;
  let name;
  let email;
  let teacher_id;
  let teacher_name;
  let noOfProblem;
  let noOfComment;

  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/me", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      id = res.data.id
      name = res.data.name
      email = res.data.email
      teacher_id = res.data.teacher.id
      teacher_name = res.data.teacher.name

      document.getElementById("email").innerText = "이메일: " + email
      document.getElementById("teacher-name").innerText = "담당 선생님: " + teacher_name
      document.getElementById("teacher-id").innerText = "선생님 아이디: " + teacher_id
      
      const res2 = await axios.get("/api/problems", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      localStorage.setItem("noOfProblem",res2.data.pageInfo.numberOfElements)
      noOfProblem = res2.data.pageInfo.numberOfElements
      document.getElementById("noOfProblem").innerText = "추천받은 문제 수: " + noOfProblem
      
      
      const res3 = await axios.get("/api/submissions", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      localStorage.setItem("noOfComment",res3.data.pageInfo.numberOfElements)
      noOfComment = res3.data.pageInfo.numberOfElements
      document.getElementById("noOfComment").innerText = "코멘트 수: " + noOfComment
    }
    getInfo();
  }, []);
  
  return (
    <main>
      <h1 id="student-title">{localStorage.CodemyName} 학생</h1>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/student">me</Link>
        <Link href="/study/student/problem">Problem</Link>
        <Link href="/study/student/comment">Comment</Link>
      </nav>
      <div className="line"></div>
      <div className="gap"></div>
      <div className="me-container">
        <p className="me-title"><b>내 정보</b></p>
        <div className="me-content">
          <ul>
            <li id="email"></li>
            <li id="teacher-name"></li>
            <li id="teacher-id"></li>
          </ul>
        </div>
        <p className="me-title"><b>푼 문제</b></p>
        <div className="me-content">
          <ul>
            <li id="noOfProblem"></li>
            <li id="noOfComment"></li>
          </ul>
        </div>
      </div>
    </main>
  );
}

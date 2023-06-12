"use client";
import Link from "next/link";
import axios from "axios";
import { useEffect, useState } from "react";
import "./page.css";

export default function Me() {
  const [me, setMe] = useState([]);
  const [teacher, setTeacher] = useState([]);
  const [problemInfo, setProblemInfo] = useState([]);
  const [commentInfo, setCommentInfo] = useState([]);

  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/me", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      setMe(res.data);
      setTeacher(res.data.teacher);
      const res2 = await axios.get("/api/problems", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      setProblemInfo(res2.data.pageInfo);
      const res3 = await axios.get("/api/submissions", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      setCommentInfo(res3.data.pageInfo);
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
            <li id="email">이메일 : {me.email}</li>
            <li id="teacher-name">선생님 : {teacher.name}</li>
            <li id="teacher-id">선생님 아이디 : {teacher.id}</li>
          </ul>
        </div>
        <p className="me-title"><b>푼 문제</b></p>
        <div className="me-content">
          <ul>
            <li id="noOfProblem">문제 개수 : {problemInfo.numberOfElements}</li>
            <li id="noOfComment">코멘트 개수 : {commentInfo.numberOfElements}</li>
          </ul>
        </div>
      </div>
    </main>
  );
}

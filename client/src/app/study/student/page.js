"use client";
import Link from "next/link";
import axios from "axios";
import { useEffect } from "react";
import "./page.css";
import Pagination from "../../components/paginate_prob"


export default function Problems() {
  let id;
  let name;
  let email;
  let teacher_id;
  let teacher_name;

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

      document.getElementById("student-title").innerText = name +" 학생"
      document.getElementById("id").innerText = "id: " + id
      document.getElementById("name").innerText = "name: " + name
      document.getElementById("email").innerText = "email: " + email
      document.getElementById("teacher-id").innerText = "teacher-id: " + teacher_id
      document.getElementById("teacher-name").innerText = "teacher-name: " + teacher_name
      
      // console.log(res.data);
      // console.log(email)
    }
    getInfo();
  }, []);
  
  return (
    <main>
      <h1 id="student-title">xxx 학생</h1>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/student">me</Link>
        <Link href="/study/student/problem">Problem</Link>
        <Link href="/study/student/comment">Comment</Link>
      </nav>
      <div className="line"></div>
      <div className="gap"></div>
      <div>
        <p id="id">id:</p>
        <p id="name">name:</p>
        <p id="email">email:</p>
        <p id="teacher-id">teacher-id:</p>
        <p id="teacher-name">teacher-name:</p>
      </div>
    </main>
  );
}

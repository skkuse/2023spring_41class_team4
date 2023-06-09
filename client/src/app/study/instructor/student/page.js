"use client";
import Link from "next/link";
import { useState, useEffect } from "react";
import axios from "axios";
import Pagination from "@/app/components/paginate";
import "./page.css";

export default function Student() {
  const [userName, setUserName] = useState("");
  const [studentList, setStudentList] = useState([]);

  useEffect(() => {
    const name = localStorage.getItem("CodemyName");
    setUserName(name);
  }, []);

  useEffect(() => {
    async function getInfo() {
      const token = localStorage.getItem("Codemy");
      const res = await axios.get("/api/teacher/students", {
        headers: {
          "X-Auth-Token": token,
        },
      });
      console.log(res.data);
      setStudentList(res.data.students);
    }
    function makeDummyData() {
      const data = [];
      for (let i = 1; i < 30; i++) {
        data.push({
          id: i,
          name: `Student${i}`,
        });
      }
      setStudentList(data);
    }
    getInfo();
    // makeDummyData();
  }, []);

  return (
    <main>
      <h1 className="mb20">{userName} 강사</h1>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/instructor">Summary</Link>
        <Link href="/study/instructor/submission">Submissions</Link>
        <Link href="/study/instructor/student">Students</Link>
      </nav>
      <div className="line"></div>
      <Pagination
        itemsPerPage={10}
        data={studentList}
        listItemOption={"student"}
      ></Pagination>
    </main>
  );
}

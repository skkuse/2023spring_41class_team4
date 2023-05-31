"use client";
import Link from "next/link";
import axios from "axios";
import { useState, useEffect } from "react";
import Pagination from "@/app/components/paginate";
import "./page.css";

export default function Submission() {
  const [submissionList, setSubmissionList] = useState([]);
  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/teacher/submissions", {
        headers: {
          "X-Auth-Token": "TEACHER1",
        },
      });
      console.log(res.data);
      setSubmissionList(res.data.submissions);
    }
    function makeDummyData() {
      const data = [];
      for (let i = 1; i < 30; i++) {
        data.push({
          id: i,
          problemId: i + 1000,
          student: {
            id: i,
            name: "Student" + i,
          },
          createdAt: "2023-05-24 1:20:00",
          status: i % 3 == 0 ? "SOLVED" : "COMMENTED",
        });
      }
      setSubmissionList(data);
    }
    // getInfo();
    makeDummyData();
  }, []);

  return (
    <main>
      <h1 className="mb20">xxx강사</h1>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/instructor">Summary</Link>
        <Link href="/study/instructor/submission">Submissions</Link>
        <Link href="/study/instructor/student">Students</Link>
      </nav>
      <div className="line"></div>
      <Pagination
        itemsPerPage={10}
        data={submissionList}
        listItemOption={"submission"}
      ></Pagination>
    </main>
  );
}

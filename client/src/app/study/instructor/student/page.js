"use client";
import Link from "next/link";
import { useEffect } from "react";
import axios from "axios";
import Pagination from "@/app/components/paginate";
import "./page.css";

export default function Student() {
  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/teacher/students", {
        headers: {
          "X-Auth-Token": "TEACHER1",
        },
      });
      console.log(res.data);
    }
    getInfo();
  }, []);
  const data = [];
  for (let i = 1; i < 30; i++) {
    data.push({
      id: i,
      name: `Student${i}`,
    });
  }
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
        data={data}
        listItemOption={"student"}
      ></Pagination>
    </main>
  );
}

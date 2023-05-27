"use client";
import Link from "next/link";
import Pagination from "@/app/components/paginate";
import "./page.css";
export default function Comments() {
  const data = [];
  for (let i = 1; i < 30; i++) {
    data.push({
      id: i,
      title: "comment " + i,
      date: " 2023-05-22",
    });
  }

  return (
    <main>
      <h1>xxx 학생</h1>
      <div className="line"></div>      
      <nav className="nav">
        <Link href="/study/student">me</Link>
        <Link href="/study/student/problem">Problem</Link>
        <Link href="/study/student/comment">Comment</Link>
      </nav>
      <div className="line"></div>
      <Pagination itemsPerPage={10} data={data}></Pagination>
    </main>
  );
}

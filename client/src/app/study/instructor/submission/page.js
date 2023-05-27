"use client";
import Link from "next/link";
import Pagination from "@/app/components/paginate";
import "./page.css";

export default function Submission() {
  const data = [];
  for (let i = 1; i < 30; i++) {
    data.push({
      id: i,
      title: "submission" + i,
      date: "2023-05-22",
    });
  }
  return (
    <main>
      <h1>xxx강사</h1>
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
        listItemOption={"submission"}
      ></Pagination>
    </main>
  );
}

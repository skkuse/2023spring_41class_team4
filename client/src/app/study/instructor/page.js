"use client";
import Link from "next/link";
import axios from "axios";
import "./page.css";
import { useEffect } from "react";

export default function Instructor() {
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
    </main>
  );
}

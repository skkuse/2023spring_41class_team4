"use client";
import Link from "next/link";
import dayjs from "dayjs";
import axios from "axios";
import Line from "@/app/components/line";
import "./page.css";
import { useEffect } from "react";

export default function Instructor() {
  const now = dayjs();
  const labels = [];
  for (let i = 6; i >= 0; i--)
    labels.push(now.subtract(i, "d").format("YYYY-MM-DD"));

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      /*
      title: {
        display: true,
        text: "Chart.js Line Chart",
      },
      */
    },
  };
  const data = {
    labels: labels,
    datasets: [
      {
        label: "Total",
        data: [0, 4, 2, 7, 5, 4, 3],
        fill: false,
        borderColor: "rgb(54, 162, 235)",
        tension: 0,
      },
      {
        label: "Solved",
        data: [0, 2, 1, 4, 2, 3, 3],
        fill: false,
        borderColor: "rgb(255, 99, 132)",
        tension: 0,
      },
      {
        label: "Commented",
        data: [0, 2, 1, 3, 3, 1, 0],
        fill: false,
        borderColor: "rgb(75, 192, 192)",
        tension: 0,
      },
    ],
  };
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
      <div className="summary-container">
        <h2>최근 7일 제출 기록</h2>
        <div style={{ width: "500px", height: "300px" }}>
          <Line options={options} data={data}></Line>
        </div>
      </div>
    </main>
  );
}

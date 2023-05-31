"use client";
import Link from "next/link";
import dayjs from "dayjs";
import axios from "axios";
import Line from "@/app/components/line";
import Bar from "@/app/components/bar";
import "./page.css";
import { useState, useEffect } from "react";

export default function Instructor() {
  const [userName, setUserName] = useState("");
  const now = dayjs();
  const labels = [];
  for (let i = 6; i >= 0; i--)
    labels.push(now.subtract(i, "d").format("YYYY-MM-DD"));

  useEffect(() => {
    const name = localStorage.getItem("CodemyName");
    setUserName(name);
  }, []);

  useEffect(() => {
    async function getInfo() {
      const token = localStorage.getItem("Codemy");
      const res = await axios.get("/api/teacher/submissions", {
        headers: {
          "X-Auth-Token": token,
        },
      });
      console.log(res.data);
    }
    // getInfo();
  }, []);

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
  const lineData = {
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

  const barOptions = {
    responsive: true,
    scales: {
      x: {
        stacked: true,
      },
      y: {
        stacked: true,
      },
    },
  };

  const barData = {
    labels: ["1004", "2104", "1024", "21096", "7517", "3198", "22756"],
    datasets: [
      {
        label: "Commented",
        data: [4, 3, 2, 2, 1, 0, 1],
        backgroundColor: [
          "rgba(255, 99, 132, 0.6)",
          "rgba(255, 159, 64, 0.6)",
          "rgba(255, 205, 86, 0.6)",
          "rgba(75, 192, 192, 0.6)",
          "rgba(54, 162, 235, 0.6)",
          "rgba(153, 102, 255, 0.6)",
          "rgba(201, 203, 207, 0.6)",
        ],
        borderColor: [
          "rgb(255, 99, 132)",
          "rgb(255, 159, 64)",
          "rgb(255, 205, 86)",
          "rgb(75, 192, 192)",
          "rgb(54, 162, 235)",
          "rgb(153, 102, 255)",
          "rgb(201, 203, 207)",
        ],
        borderWidth: 1,
      },
      {
        label: "Solved",
        data: [9, 6, 5, 3, 3, 2, 1],
        backgroundColor: [
          "rgba(255, 99, 132, 0.2)",
          "rgba(255, 159, 64, 0.2)",
          "rgba(255, 205, 86, 0.2)",
          "rgba(75, 192, 192, 0.2)",
          "rgba(54, 162, 235, 0.2)",
          "rgba(153, 102, 255, 0.2)",
          "rgba(201, 203, 207, 0.2)",
        ],
        borderColor: [
          "rgb(255, 99, 132)",
          "rgb(255, 159, 64)",
          "rgb(255, 205, 86)",
          "rgb(75, 192, 192)",
          "rgb(54, 162, 235)",
          "rgb(153, 102, 255)",
          "rgb(201, 203, 207)",
        ],
        borderWidth: 1,
      },
    ],
  };

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
      <div className="summary-container">
        <h2>최근 7일 제출 기록</h2>
        <div style={{ width: "500px", height: "300px" }}>
          <Line data={lineData} options={options}></Line>
        </div>
        <h2>최근 7일 많이 제출된 문제</h2>
        <div style={{ width: "500px", height: "300px" }}>
          <Bar data={barData} options={barOptions}></Bar>
        </div>
      </div>
    </main>
  );
}

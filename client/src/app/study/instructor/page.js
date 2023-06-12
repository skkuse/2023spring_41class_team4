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
  const [teacherCode, setTeacherCode] = useState("");
  const [dateCount, setDateCount] = useState([
    [0, 0],
    [0, 0],
    [0, 0],
    [0, 0],
    [0, 0],
    [0, 0],
    [0, 0],
  ]);
  const [problemCount, setProblemCount] = useState([]);
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
      const submissions = res.data.submissions;
      let _dateCount = [
        [0, 0],
        [0, 0],
        [0, 0],
        [0, 0],
        [0, 0],
        [0, 0],
        [0, 0],
      ];
      let _problemCount = {};
      console.log(submissions);
      for (let i = 0; i < submissions.length; i++) {
        const date = dayjs(submissions[i].createdAt).format("YYYY-MM-DD");
        const status = submissions[i].status;
        for (let j = 0; j < 7; j++) {
          if (date === labels[j]) {
            if (status === "SOLVE") {
              _dateCount[j][0]++;
            } else {
              _dateCount[j][1]++;
            }
            break;
          }
        }
      }
      setDateCount(_dateCount);
      for (let i = 0; i < submissions.length; i++) {
        const problemId = submissions[i].problemId;
        const status = submissions[i].status;
        if (status === "SOLVE") {
          if (_problemCount[problemId]) {
            _problemCount[problemId][0]++;
          } else {
            _problemCount[problemId] = [1, 0];
          }
        } else {
          if (_problemCount[problemId]) {
            _problemCount[problemId][1]++;
          } else {
            _problemCount[problemId] = [0, 1];
          }
        }
      }
      let _sortedProblemCount = [];
      for (let key in _problemCount) {
        _sortedProblemCount.push([key, _problemCount[key]]);
      }
      _sortedProblemCount.sort(
        (a, b) => b[1][0] + b[1][1] - (a[1][0] + a[1][1])
      );
      setProblemCount(_sortedProblemCount.slice(0, 7));
    }
    async function getInstructorInfo() {
      const token = localStorage.getItem("Codemy");
      const res = await axios.get("/api/teacher/me", {
        headers: {
          "X-Auth-Token": token,
        },
      });
      console.log(res.data);
      setTeacherCode(res.data.teacherCode);
    }
    getInfo();
    getInstructorInfo();
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
        data: dateCount.map((x) => x[0] + x[1]),
        fill: false,
        borderColor: "rgb(54, 162, 235)",
        tension: 0,
      },
      {
        label: "Solved",
        data: dateCount.map((x) => x[0]),
        fill: false,
        borderColor: "rgb(255, 99, 132)",
        tension: 0,
      },
      {
        label: "Commented",
        data: dateCount.map((x) => x[1]),
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
    labels: problemCount.map((x) => x[0]),
    datasets: [
      {
        label: "Commented",
        data: problemCount.map((x) => x[1][1]),
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
        data: problemCount.map((x) => x[1][0]),
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
      <div>
        <h1
          className="mb20"
          style={{ display: "inline-block", marginRight: "20px" }}
        >
          {userName} 강사
        </h1>
        강사코드: {teacherCode}
      </div>
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

"use client";
import CodeViwer from "@/app/components/codeViwer";
import Radar from "@/app/components/radar";
import { useEffect } from "react";
import "./page.css";

export default function SubmissionItem({ params }) {
  useEffect(() => {
    Prism.highlightAll();
  }, []);
  const code = `#include<bits/stdc++.h>
using namespace std;
  
int main(){
  cout << "testing";

  return 0;
}
  `;
  const feedback = {
    id: 1,
    problemId: 1,
    student: {
      id: 1,
      name: "Student1",
    },
    createdAt: "2023-05-24 1:20:00",
    status: "SOLVED",
    language: "python3",
    content: "print(a + b)",
    feedback: {
      id: 1,
      overview: "완벽한 코드입니다.",
      achievement: {
        efficiency: 100,
        readability: 79,
        correctness: 90,
        scalability: 97,
        modularity: 89,
        security: 89,
      },
    },
  };

  const labels = [
    "efficiency",
    "readability",
    "correctness",
    "scalability",
    "modularity",
    "security",
  ];

  const data = {
    labels: labels,
    datasets: [
      {
        label: "My First Dataset",
        data: labels.map((x) => feedback.feedback.achievement[x]),
        fill: true,
        backgroundColor: "rgba(255, 99, 132, 0.2)",
        borderColor: "rgb(255, 99, 132)",
        pointBackgroundColor: "rgb(255, 99, 132)",
        pointBorderColor: "#fff",
        pointHoverBackgroundColor: "#fff",
        pointHoverBorderColor: "rgb(255, 99, 132)",
      },
    ],
  };

  return (
    <main>
      <div className="student-status">
        <h1>xxx 학생</h1>
        <div className="flex-col margin-top">
          <div className="flex-col margin-top">
            <h1>Code</h1>
            <div>
              <CodeViwer code={code} language={"cpp"}></CodeViwer>
            </div>
          </div>
          <div className="flex-col margin-top">
            <h1>ChatGPT Feedback</h1>
            <div className="flex-row">
              <div style={{ width: "300px", height: "300px" }}>
                <Radar data={data}></Radar>
              </div>
              <div className="feedback-container">
                {feedback && feedback.feedback.overview}
              </div>
            </div>
          </div>
          <div className="flex-col margin-top">
            <h1>Comment</h1>
            <textarea
              className="mt10"
              rows="10"
              placeholder="comment"
            ></textarea>
            <div className="submit-button-container">
              <button className="submit-button" type="button">
                Submit
              </button>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}

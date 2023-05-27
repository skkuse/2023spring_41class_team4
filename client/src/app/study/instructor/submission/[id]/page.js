"use client";
import CodeViwer from "@/app/components/codeViwer";
import Radar from "@/app/components/radar";
import "./page.css";
import { useEffect } from "react";

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
  const data = {
    labels: [
      "Eating",
      "Drinking",
      "Sleeping",
      "Designing",
      "Coding",
      "Cycling",
    ],
    datasets: [
      {
        label: "My First Dataset",
        data: [65, 90, 81, 56, 55, 80],
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
            <div style={{ width: "300px", height: "300px" }}>
              <Radar data={data}></Radar>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}

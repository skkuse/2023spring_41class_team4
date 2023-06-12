"use client";
import CodeViwer from "@/app/components/codeViwer";
import Radar from "@/app/components/radar";
import axios from "axios";
import { useState, useEffect } from "react";
import "./page.css";

export default function SubmissionItem({ params }) {
  const [feedback, setFeedback] = useState({});
  const [comment, setComment] = useState({});
  const [defaultComment, setDefaultComment] = useState("");

  useEffect(() => {
    setDefaultComment("아직 코멘트가 등록되지 않았습니다.")
  })

  useEffect(() => {
    Prism.highlightAll();
    console.log("Hi");
    async function getInfo() {
      const res = await axios.get(
        `/api/submissions/${params.id}`,
        {
          headers: {
            "X-Auth-Token": localStorage.Codemy,
          },
        }
        );
        console.log(res.data);
        setFeedback(res.data);
        setComment(res.data.comment);
    }
    function makeDummyData() {
      const _feedback = {
        id: 1,
        problemId: 1,
        student: {
          id: 1,
          name: "Student1",
        },
        createdAt: "2023-05-24 1:20:00",
        status: "SOLVED",
        language: "cpp",
        content: `#include<bits/stdc++.h>
using namespace std;
          
int main(){
  cout << "testing";
      
return 0;
}`,
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
      setFeedback(_feedback);
      console.log(_feedback);
    }
    getInfo();
    // makeDummyData();
  }, []);

  const labels = [
    "efficiency",
    "readability",
    "correctness",
    "scalability",
    "modularity",
    "security",
  ];

  function getComment(status){
    console.log(status);
    if (status=="SOLVE"){
      return "아직 코멘트가 등록되지 않았습니다.";
    }
    else{
      if (comment){
        return comment.content;
      }
      else{
        return "Commented"
      }
    }
  }

  return (
    <main>
      <div className="student-status">
        {/* <h1>{localStorage.CodemyName} 학생</h1> */}
        <div className="flex-col">
          <div className="flex-col margin-top">
            <h1>Submitted Code</h1>
            <div>
              <CodeViwer
                code={feedback && feedback.content}
                language={feedback && feedback.language}
              ></CodeViwer>
            </div>
          </div>
          <div className="flex-col margin-top">
            <h1>Comment</h1>
            <div className="comment-container">
              {feedback && getComment(feedback.status)}

            </div>
          </div>
        </div>
      </div>
    </main>
  );
}

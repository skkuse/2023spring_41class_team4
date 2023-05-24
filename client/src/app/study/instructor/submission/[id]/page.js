"use client";
import CodeViwer from "@/app/components/codeViwer";
import Prism from "prismjs";
import "prismjs/themes/prism-tomorrow.css";
import "prismjs/components/prism-c";
import "prismjs/components/prism-cpp";
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
  return (
    <main>
      <div className="student-status">
        <h1>xxx 학생</h1>
        <CodeViwer code={code} language={"cpp"}></CodeViwer>
      </div>
    </main>
  );
}

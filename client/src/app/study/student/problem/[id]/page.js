"use client"
import Link from "next/link";
import "./page.css";
import CodeEditor from "@/app/components/codeEditor";
// import { useEffect } from "react";
// import Prism from "prismjs"

export default function ProblemItem({ params }) {
  const code = `#include<bits/stdc++.h>
using namespace std;
  
int main(){
  cout << "testing";

  return 0;
}
  `;
  return (
    <CodeEditor code={code}></CodeEditor>

  );

  // return <>{params.id}</>;
}


"use client"
import React from 'react';
import { useState, useEffect } from "react";
import CodeMirror from '@uiw/react-codemirror';
import { python } from '@codemirror/lang-python';
import axios from "axios";
import "./page.css";
import Link from "next/link";

export default function ProblemItem({ params }) {
  const [problemId, setProblemId] = useState(0);

  // 문제 정보 가져오기
  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/problems", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      let problem = res.data.problems[params.id-1];
      localStorage.setItem("problemId",problem.id)
      document.getElementById("title").innerText = problem.id +": "+ problem.title;
      document.getElementById("link").innerHTML = "링크: <a href='"+problem.link+"'><b>"+problem.link+"</b></a>";
    }
    getInfo();
  }, []);

  // codeMirror 기본 코드
  const pyFuction = `print(A+B)
  
  
  
  
  `  

  const [code, setCode] = useState(pyFuction); // CodeMirror에 입력되는 값들 ( 기본으로 pyFunction을 가진다 )
  const [result, setResult] = useState([]); // 채점 후 결과를 저장하는 곳

  const onChange = (e) => {
    setCode(e) // CodeMirror의 값이 변할 때마다 호출되어서 값이 저장된다.
  }

  const runCode = async () => {
	  // 제출버튼을 누르면 axios를 통해 서버로 코드데이터가 넘어간다.
    const res = await axios.post("/api/problems/"+localStorage.problemId+"/submit", {
      headers: {
        "X-Auth-Token": localStorage.Codemy,
        "Content-Type":"application/json",
      },
      data: {
        "language": "python3",
        "content": code,
      },
    })
    .then((response) => {
      console.log(response.data);
    })
    .catch((err) => {
      console.error(err);
    });
  };

// CodeMirror에서 MarkDown도 지원해줘서 문제에 대한 설명을 MarkDown으로 표시할 수 있었다.
  const markdown = `
## JavaScript 문제풀이 
...
`

  const markdownExampel = `
 #### 출력 예시  
...`

  return (
    <main>
      <div>id : {params.id}</div>
      <div>
        <h3 id="title"></h3>
        <p id="link">링크: </p>
        <p></p>
      </div>
      <div><h3>Answer Code</h3></div>
      <div className='codingIDE'>
        <CodeMirror
          className='CodeMirror'
          lineNumber={true} // 줄옆에 숫자 
          value={code} // 입력된 값을 code라는 변수에 담았다
          // extensions={[python({ jsx: true })]} // 입력값의 종류 
          extensions={[python()]} // 입력값의 종류 
          onChange={onChange} //값이 변할때마다 호출되는 함수 
          // theme={okaidia} // 테마 
          gutter={true} // 자동생성
        />
      </div>

      <div className="BtnArea" onClick={runCode}>Submit</div>

    </main>

  );
}

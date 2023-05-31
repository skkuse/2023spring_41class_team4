"use client"
import React from 'react';
import { useState, useEffect } from "react";
import CodeMirror from '@uiw/react-codemirror';
// import { javascript } from '@codemirror/lang-javascript';
import { python } from '@codemirror/lang-python';
import axios from "axios";
import "./page.css";

var problemId;

export default function ProblemItem({ params }) {
  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/problems", {
        headers: {
          "X-Auth-Token": "STUDENT1",
        },
      });
      let problem = res.data.problems[params.id-1];
      problemId = problem.id;
      // document.getElementById("title").innerText = problem.id;
      document.getElementById("title").innerText = problem.id +": "+ problem.title;
      document.getElementById("link").innerText = "Link: "+problem.link;

    }
    getInfo();
  }, []);

  const pyFuction = `print(A+B)
  
  
  
  
  `  

  const [code, setCode] = useState(pyFuction); // CodeMirror에 입력되는 값들 ( 기본으로 jsFunction을 가진다 )
  const [result, setResult] = useState([]); // 채점 후 결과를 저장하는 곳

  const onChange = (e) => {
    setCode(e) // CodeMirror의 값이 변할 때마다 호출되어서 값이 저장된다.
  }

  const runCode = async () => {
	// 제출버튼을 누르면 axios를 통해 서버로 코드데이터가 넘어간다.
    console.log(problemId);
    await axios.post("/api/problems/"+problemId+"/submit", {
      headers: {
        "X-Auth-Token": "STUDENT1",
      },
      "data": {
        "language": "python3",
        "content": code,
      } })
      .then((result) => {
        console.log(result.data)
        setResult(result.data.result)
      })
      .catch((err) => {
        console.log(err)
      })
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
        <h3 id="title">Title</h3>
        <p id="link">Link</p>
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

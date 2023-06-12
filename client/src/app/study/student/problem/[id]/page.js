"use client";
import React from "react";
import { useState, useEffect } from "react";
import CodeMirror from "@uiw/react-codemirror";
import { python } from "@codemirror/lang-python";
import axios from "axios";
import "./page.css";
import Link from "next/link";

export default function ProblemItem({ params }) {
  const [problem, setProblem] = useState([]);

  // 문제 정보 가져오기
  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/problems/" + params.id, {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      console.log(res.data);
      setProblem(res.data);
    }
    getInfo();
    console.log(problem);
  }, []);

  // codeMirror 기본 코드
  const pyFuction = `print(A+B)
  
  



  
  
  
  `;

  const [code, setCode] = useState(pyFuction); // CodeMirror에 입력되는 값들 ( 기본으로 pyFunction을 가진다 )
  const [result, setResult] = useState([]); // 채점 후 결과를 저장하는 곳

  const onChange = (e) => {
    setCode(e); // CodeMirror의 값이 변할 때마다 호출되어서 값이 저장된다.
  };

  const runCode = async () => {
    // 제출버튼을 누르면 axios를 통해 서버로 코드데이터가 넘어간다.
    console.log(localStorage.Codemy);
    console.log(code);
    const res = await axios
      .post(
        "/api/problems/" + problem.id + "/submit",
        {
          language: "python3",
          content: code,
        },
        {
          headers: {
            "X-Auth-Token": localStorage.Codemy,
          },
        }
      )
      .then((response) => {
        console.log(response);
        alert("제출이 완료되었습니다!");
      })
      .catch((err) => {
        console.error(err);
      });
  };

  // CodeMirror에서 MarkDown도 지원해줘서 문제에 대한 설명을 MarkDown으로 표시할 수 있었다.
  const markdown = `
## JavaScript 문제풀이 
...
`;

  const markdownExampel = `
 #### 출력 예시  
...`;

  return (
    <main>
      {/* <div>id : {params.id}</div> */}
      <div className="sub-title">
        <h2 id="title">{problem.title}</h2>
      </div>
      <div className="line"></div>
      <div className="description">
        <p id="problemId">문제 번호 : {problem.id}</p>
        <p>
          <Link href={String(problem.content)}>
            <b>문제 풀러 가기</b>
          </Link>
        </p>
      </div>
      <div className="sub-title submit-container">
        <h2>제출</h2>
      </div>
      <div className="line"></div>

      <div className="codingIDE">
        <CodeMirror
          className="CodeMirror"
          lineNumber={true} // 줄옆에 숫자
          value={code} // 입력된 값을 code라는 변수에 담았다
          // extensions={[python({ jsx: true })]} // 입력값의 종류
          extensions={[python()]} // 입력값의 종류
          onChange={onChange} //값이 변할때마다 호출되는 함수
          // theme={okaidia} // 테마
          gutter={true} // 자동생성
        />
      </div>
      <div className="submit-button-wrapper">
        <div className="submit-button" onClick={runCode}>
          Submit
        </div>
      </div>
    </main>
  );
}

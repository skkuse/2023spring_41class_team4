import Link from "next/link";
import "./page.css";

export default function Student() {
  return (
    <main>
      <div>
        <div className="problem-title"><h1>xxx 학생 추천 문제 목록</h1></div>
        <div className="problem-box">
          <table className="problem-table">
            <thead>
              <tr>
                <th style={{width: "10%"}} data-sort="int">단계</th>
                <th style={{width: "10%"}} data-sort="int">문제 번호</th>
                <th style={{width: "30%"}} data-sort="string">제목</th>
                <th style={{width: "20%"}} data-sort="string">정보</th>
                <th style={{width: "10%"}} data-sort="int">정답</th>
                <th style={{width: "10%"}} data-sort="int">제출</th>
                <th style={{width: "10%"}} data-sort="float">정답 비율</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td className="list-problem-id">2557</td>
                <td><a href="/problem/2557">Hello World</a></td>
                <td><a href="/status?problem_id=2557&result_id=4">383051</a></td>
                <td><a href="/status?problem_id=2557">948598</a></td>
                <td>39.534</td>
                <td>0.3333</td>
              </tr>
              <tr>
                <td>2</td>
                <td className="list-problem-id">2557</td>
                <td><a href="/problem/2557">Hello World</a></td>
                <td><a href="/status?problem_id=2557&result_id=4">383051</a></td>
                <td><a href="/status?problem_id=2557">948598</a></td>
                <td>39.534</td>
                <td>0.3333</td>
              </tr>
              <tr>
                <td>3</td>
                <td className="list-problem-id">2557</td>
                <td><a href="/problem/2557">Hello World</a></td>
                <td><a href="/status?problem_id=2557&result_id=4">383051</a></td>
                <td><a href="/status?problem_id=2557">948598</a></td>
                <td>39.534</td>
                <td>0.3333</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>
  );
}

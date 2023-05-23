import Link from "next/link";
import "./page.css";

export default function Student() {
  return (
    <main>
      <div className="student-status">
        <h1>xxx 학생</h1>
        <div className="code-numbers">
          <p>지금까지 제출한 코드 수</p>
          <p>피드백 받은 코드 수</p>
        </div>
      </div>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/student/problems">Problems</Link>
        <Link href="/study/student/comments">Comments</Link>
      </nav>
      <div className="line"></div>
    </main>
  );
}

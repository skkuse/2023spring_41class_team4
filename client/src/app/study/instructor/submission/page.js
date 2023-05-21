import Link from "next/link";
import "./page.css";

export default function Submission() {
  return (
    <main>
      <h1>xxx강사</h1>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/instructor/summary">Summary</Link>
        <Link href="/study/instructor/submission">Submission</Link>
        <Link href="/study/instructor/student">Student Info</Link>
      </nav>
      <div className="line"></div>
    </main>
  );
}

import Link from "next/link";

export default function Study() {
  return (
    <main>
      <div className="content">
        스터디 페이지
        <p>
          <Link href="/study/instructor">강사 페이지 링크</Link>
          <Link href="/study/student">학생 페이지 링크</Link>
        </p>
      </div>
    </main>
  );
}

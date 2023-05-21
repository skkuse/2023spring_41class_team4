import Link from "next/link";

export default function Study() {
  return (
    <main>
      <p>스터디 페이지</p>
      <p>
        <Link href="/study/instructor">
          <b>강사 페이지 링크</b>
        </Link>
      </p>
      <p>
        <Link href="/study/student">
          <b>학생 페이지 링크</b>
        </Link>
      </p>
    </main>
  );
}

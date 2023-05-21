import Link from "next/link";

export default function Login() {
  return (
    <main>
      로그인 페이지
      <p>
        <Link href="/register">아이디가 없는 경우 회원가입 링크</Link>
      </p>
    </main>
  );
}

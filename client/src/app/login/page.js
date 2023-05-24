import Link from "next/link";
import "./page.css";

export default function Login() {
  return (
    <main>
      <div class="containerLog">
        <div class="loginBox">
          <h3 id="pageTitle">Login</h3>
          <form class="loginForm" name="loginForm" method="post"  action="https://www.formbackend.com/f/664decaabbf1c319">
            <input type="text" id="loginEmail" name="loginEmail" placeholder="Email"></input>
            <input type="password" id="loginPass" name="loginPass" placeholder="Password"></input>
            <button type="submit" id="btnLogin">로그인</button>
          </form>
          <p>
            아이디가 없는 경우?
            <Link href="/register" id="signupLink">회원가입</Link>
          </p>
        </div>
      </div>
    </main>
  );
}

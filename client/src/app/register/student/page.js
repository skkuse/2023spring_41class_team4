import Link from "next/link";
import "./page.css";

export default function Register() {
  return (
    <main>
      <div class="containerLog">
      <div class="signupBox">
        <h3 id="pageTitle">Sign Up</h3>
        <form class="signupForm" id="signupForm" name="signupForm" method="post" action="signup.jsp">
          <input type="email" id="signupEmail" name="signupEmail" class="signupCon" placeholder="Email"></input>
          <input type="password" id="signupPass" name="signupPass" class="signupCon" placeholder="Password"></input>
          <input type="password" id="signupPassCon" name="signupPassCon" class="signupCon" placeholder="Confirm password"></input>
          <input type="text" id="signupName" name="signupName" class="signupCon" placeholder="Name"></input>
          <input type="text" id="signupGithubId" name="signupGithubId" class="signupCon" placeholder="Github ID"></input>
          <input type="text" id="signupTeacherId" name="signupTeacherId" class="signupCon" placeholder="Teacher ID"></input>
          <button type="submit" id="btnSignup">가입하기</button>
          <p>
            아이디가 이미 있으면
            <Link href="/login" id="loginLink">로그인</Link>
          </p>
        </form>
      </div>
    </div>
    </main>
  );
}

"use client";
import Link from "next/link";
import "./page.css";
import axios from "axios";

import { useState } from "react";

export default function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleName = (e) => {
    setName(e.target.value);
  };

  const handleEmail = (e) => {
    setEmail(e.target.value);
  };

  const handlePassword = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (name === "" || email === "" || password === "") {
      console.log("empty form");
    } else {
      console.log("done");

      try {
        const response = await axios.post("/api/teacher/users", {
          email: email,
          password: password,
          name: name,
        });
        console.log(response);
        window.location.href = "/login";
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <main style={{ padding: "82px 0" }}>
      <div class="containerLog">
        <div class="signupBox">
          <h3 id="pageTitle">회원가입</h3>
          <form class="signupForm">
            <input
              onChange={handleName}
              className="input"
              value={name}
              type="text"
              placeholder="Name"
            />

            <input
              onChange={handleEmail}
              className="input"
              value={email}
              type="email"
              placeholder="Email"
            />

            <input
              onChange={handlePassword}
              className="input"
              value={password}
              type="password"
              placeholder="Password"
            />

            <button onClick={handleSubmit} id="btnSignup" type="submit">
              가입하기
            </button>
            <p>
              아이디가 이미 존재한다면
              <Link href="/login" id="loginLink">
                로그인
              </Link>
            </p>
          </form>
        </div>
      </div>
    </main>
  );
}

"use client";
import Link from "next/link";
import axios from "axios";
import { useState } from "react";
import "./page.css";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [status, setStatus] = useState("");

  const handleEmail = (e) => {
    setEmail(e.target.value);
  };

  const handlePassword = (e) => {
    setPassword(e.target.value);
  };

  const handleStatus = (e) => {
    setStatus(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (email === "" || password === "" || status == "") {
      console.log("Lacking data");
    } else {
      console.log("done");
      let url = "";
      if (status == "Teacher") {
        url = "/api/teacher/users/login";
      } else {
        url = "/api/users/login";
      }
      console.log(url);
      try {
        const response = await axios.post(url, {
          email: email,
          password: password,
        });
        console.log(response);
        localStorage.setItem("Codemy", response.data.token);
        const token = response.data.token;
        if (token.substr(0, 7) === "TEACHER") {
          url = "/api/teacher/me";
        } else {
          url = "/api/me";
        }
        const res = await axios.get(url, {
          headers: {
            "X-Auth-Token": token,
          },
        });
        localStorage.setItem("CodemyName", res.data.name);
        console.log("a");
        window.location.href = "/";
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <main style={{ padding: "83px 0" }}>
      <div class="containerLog">
        <div class="loginBox">
          <h3 id="pageTitle">로그인</h3>
          <form class="loginForm">
            <input
              onChange={handleEmail}
              className="input"
              value={email}
              type="text"
              placeholder="Email"
            />
            <input
              onChange={handlePassword}
              className="input"
              value={password}
              type="password"
              placeholder="Password"
            />
            <div class="radioForm">
              <label for="Student">
                <input
                  onChange={handleStatus}
                  type="radio"
                  name="radioType"
                  id="Student"
                  value="Student"
                ></input>
                <span>Student</span>
              </label>
              <label for="Teacher">
                <input
                  onChange={handleStatus}
                  type="radio"
                  name="radioType"
                  id="Teacher"
                  value="Teacher"
                ></input>
                <span>Teacher</span>
              </label>
            </div>
            <button onClick={handleSubmit} id="btnLogin" type="submit">
              로그인
            </button>
          </form>
          <p>
            아이디가 없는 경우?
            <Link href="/register" id="signupLink">
              회원가입
            </Link>
          </p>
        </div>
      </div>
    </main>
  );
}

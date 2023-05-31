"use client";
import Link from "next/link";
import axios from "axios";
import { useState, useEffect } from "react";
import "./header.css";

export default function Header() {
  const [userName, setUserName] = useState("");
  const [studyURL, setStudyURL] = useState("/");
  useEffect(() => {
    async function getInfo(token) {
      let url = "";
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
      console.log(res.data);
      setUserName(res.data.name);
    }
    const name = localStorage.getItem("CodemyName");
    const token = localStorage.getItem("Codemy");
    if (name !== null) {
      setUserName(name);
      if (token.substr(0, 7) === "TEACHER") {
        setStudyURL("/study/instructor");
      } else {
        setStudyURL("/study/student");
      }
    } else if (token !== null) {
      getInfo(token);
    } else {
      setUserName("Login");
      setStudyURL("/login");
    }
  }, []);

  const logoutHandler = () => {
    setUserName("Login");
    localStorage.removeItem("Codemy");
    localStorage.removeItem("CodemyName");
  };

  return (
    <div className="header-flex-row header">
      <div className="header-flex-row gap">
        <Link href="/" className="text pointer">
          Home
        </Link>
        <Link href={studyURL} className="text pointer">
          Study
        </Link>
      </div>
      <Link href="/" className="pointer title">
        Codemy
      </Link>
      {userName === "Login" ? (
        <Link href="/login" className="login-box pointer">
          {userName}
        </Link>
      ) : (
        <div className="login-box pointer" onClick={logoutHandler}>
          {userName}
        </div>
      )}
    </div>
  );
}

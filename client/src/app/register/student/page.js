"use client";
import Link from "next/link";
import "./page.css";
import axios from 'axios';

import { useState } from 'react';

export default function Register() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [githubAccount, setGithubAccount] = useState('');
  const [teacherCode, setTeacherCode] = useState('');
 
  const handleName = (e) => {
      setName(e.target.value);
    };
 
  const handleEmail = (e) => {
      setEmail(e.target.value);
  };
 
  const handlePassword = (e) => {
      setPassword(e.target.value);
  };

  const handleGithubAccount = (e) => {
    setGithubAccount(e.target.value);
  };

  const handleTeacherCode = (e) => {
    setTeacherCode(e.target.value);
  };
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (name === '' || email === '' || password === '' || githubAccount == '' || teacherCode == '') {
      console.log("Lacking data")
    } else {
      console.log("done")
    
    try {
      const response = await axios.post('/api/users', {
        "email": email,
	      "password": password,
	      "name": name,
        "githubAccount": githubAccount,
	      "teacherCode": teacherCode
      });
      console.log(response);
      window.location.href = '/login';
    } catch (error) {
      console.error(error);
    }
    
  }
  };
  return (
    <main>
      <div class="containerLog">
      <div class="signupBox">
        <h3 id="pageTitle">회원가입</h3>
        <form class="signupForm">
          <input onChange={handleName} className="input" value={name} type="text" placeholder="Name" />
          <input onChange={handleEmail} className="input" value={email} type="text" placeholder="Email" />
          <input onChange={handlePassword} className="input" value={password} type="password" placeholder="Password" />
          <input onChange={handleGithubAccount} className="input" value={githubAccount} type="text" placeholder="Github ID" />
          <input onChange={handleTeacherCode} className="input" value={teacherCode} type="text" placeholder="Teacher ID" />
          <button onClick={handleSubmit} id="btnSignup" type="submit">가입하기</button>
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

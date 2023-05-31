"use client";
import Link from "next/link";
import axios from "axios";
import { useEffect, useState } from "react";
import "./page.css";
import Pagination from "../../../components/paginate_prob"


var data = [];
var pageInfo;
var itemsPerPage;
export default function Problems() {
  // const [email, setEmail] = useState("");

  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/problems", {
        headers: {
          "X-Auth-Token": localStorage.Codemy,
        },
      });
      console.log("debug");
      console.log(res.data);
      pageInfo = res.data.pageInfo;
      itemsPerPage = pageInfo.pageSize;
      // totalPage
      // currentpage
      // pageSize
      // numberOfElements
      data = []
      for (let i = 0; i < pageInfo.numberOfElements; i++) {
        let problem = res.data.problems[i];
        console.log(problem);
        data.push({
          id: i+1,
          pNumber: problem.id,
          title: problem.title,
          link: problem.link,
        });
      }
    }
    getInfo();
  }, []);
  return (
    <main>
      <h1>xxx 학생</h1>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/student">me</Link>
        <Link href="/study/student/problem">Problem</Link>
        <Link href="/study/student/comment">Comment</Link>
      </nav>
      <div className="line"></div>
      <div className="gap"></div>
      <Pagination itemsPerPage={itemsPerPage} data={data}></Pagination>
    </main>
  );
}

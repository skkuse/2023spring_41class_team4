"use client";
import Link from "next/link";
import axios from "axios";
import { useEffect, useState } from "react";
import "./page.css";
import Pagination from "@/app/components/paginate_prob"


var data = [];
var pageInfo;
var itemsPerPage;
export default function Problems() {
  const [problemList, setProblemList] = useState([]);

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
      setProblemList(res.data.problems)
      // totalPage
      // currentpage
      // pageSize
      // numberOfElements
    }
    getInfo();
  }, []);
  return (
    <main>
      <h1>{localStorage.CodemyName} 학생</h1>
      <div className="line"></div>
      <nav className="nav">
        <Link href="/study/student">me</Link>
        <Link href="/study/student/problem">Problem</Link>
        <Link href="/study/student/comment">Comment</Link>
      </nav>
      <div className="line"></div>
      <div className="gap"></div>
      <Pagination itemsPerPage={10} data={problemList}></Pagination>
    </main>
  );
}

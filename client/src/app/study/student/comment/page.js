"use client";
import Link from "next/link";
import axios from "axios";
import { useState,useEffect } from "react";
import Pagination from "@/app/components/paginate_comment";
import "./page.css";

var pageInfo;
var itemsPerPage;
var data=[];

export default function Comments() {

  useEffect(() => {
    async function getInfo() {
      const res = await axios.get("/api/submissions", {
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
      data = [];
      for (let i = 0; i < pageInfo.numberOfElements; i++) {
        let submission = res.data.submissions[i];
        console.log(submission);
        data.push({
          id: i+1,
          name: localStorage.CodemyName,
          problemId: submission.problemId,
          createdAt: submission.createdAt,
          status: submission.status,
        });
      }
    }
    getInfo();
  }, []);
  // const data = [];
  // for (let i = 1; i < 30; i++) {
  //   data.push({
  //     id: i,
  //     title: "comment " + i,
  //     date: " 2023-05-22",
  //   });
  // }

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
      <Pagination itemsPerPage={10} data={data}></Pagination>
    </main>
  );
}

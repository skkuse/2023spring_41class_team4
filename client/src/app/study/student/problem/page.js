"use client";
import Link from "next/link";
import "./page.css";
import Pagination from "../../../components/paginate_prob"

export default function Problems() {
  const data = [];
  for (let i=1;i<30;i++){
    data.push({
      id: i,
      probNo: 2557,
      title: "Hello World",
      info: 383051,
      ans: 948598,
      submit: 39.534,
      ratio: 0.3333
    })
  }
  // const base_url = "https://port-0-codemy-7e6o2clhzvliku.sel4.cloudtype.app/"
  // const url = base_url + "/problems"
  // fetch()
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
      <Pagination itemsPerPage={10} data={data}></Pagination>
    </main>
  );
}

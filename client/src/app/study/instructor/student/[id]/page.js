"use client";
import Link from "next/link";
import Pagination from "@/app/components/paginate";

export default function StudentItem({ params }) {
  const data = [];
  for (let i = 1; i < 30; i++) {
    data.push({
      id: i,
      title: "submission" + i,
      date: "2023-05-22",
    });
  }
  return (
    <main>
      {params.id}
      <h1>xxx학생 정보</h1>
      <h2>최근 제출 내역</h2>
      <Pagination
        itemsPerPage={5}
        data={data}
        listItemOption={"submission"}
      ></Pagination>
    </main>
  );
}

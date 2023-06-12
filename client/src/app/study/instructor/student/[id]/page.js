"use client";
import Pagination from "@/app/components/paginate";
import axios from "axios";
import { useState, useEffect } from "react";
import { useSearchParams } from "next/navigation";

export default function StudentItem({ params }) {
  let searchParams = useSearchParams();
  const [submissionList, setSubmissionList] = useState([]);

  useEffect(() => {
    async function getInfo() {
      const token = localStorage.getItem("Codemy");
      const res = await axios.get(`/api/teacher/submissions`, {
        headers: {
          "X-Auth-Token": token,
        },
        params: {
          studentId: params.id,
        },
      });
      setSubmissionList(res.data.submissions);
    }
    function makeDummyData() {
      const data = [];
      for (let i = 1; i < 30; i++) {
        data.push({
          id: i,
          problemId: i + 1000,
          student: {
            id: i,
            name: "Student1",
          },
          createdAt: "2023-05-24 1:20:00",
          status: "SOLVED",
        });
      }
      setSubmissionList(data);
    }
    getInfo();
    // makeDummyData();
  }, []);

  return (
    <main>
      <h1 className="mb20">{searchParams.get("name")} 학생 정보</h1>
      <h2>최근 제출 내역</h2>
      <Pagination
        itemsPerPage={5}
        data={submissionList}
        listItemOption={"submission"}
      ></Pagination>
    </main>
  );
}

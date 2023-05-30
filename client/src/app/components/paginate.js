"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import ReactPaginate from "react-paginate";
import "./paginate.css";

// Example items, to simulate fetching from another resources.

function SubmissionItems({ currentItems }) {
  return (
    <div style={{ width: "100%" }}>
      <header className="submission-header submission-container">
        <ul>
          <li>#</li>
          <li>학생</li>
          <li>문제</li>
          <li>제출일</li>
          <li>상태</li>
        </ul>
      </header>
      <div className="items-container">
        {currentItems &&
          currentItems.map((item) => (
            <Link
              href={`/study/instructor/submission/${item.id}`}
              className="item submission-container"
            >
              <ul>
                <li>{item.id}</li>
                <li>{item.student.name}</li>
                <li>{item.problemId}</li>
                <li>{item.createdAt}</li>
                <li>{item.status}</li>
              </ul>
            </Link>
          ))}
      </div>
    </div>
  );
}

function StudentItems({ currentItems }) {
  return (
    <div className="items-container">
      {currentItems &&
        currentItems.map((item) => (
          <Link href={`/study/instructor/student/${item.id}`} className="item">
            {item.title}
            {item.date}
          </Link>
        ))}
    </div>
  );
}

export default function PaginatedItems({ itemsPerPage, data, listItemOption }) {
  // Here we use item offsets; we could also use page offsets
  // following the API or data you're working with.
  const [itemOffset, setItemOffset] = useState(0);

  // Simulate fetching items from another resources.
  // (This could be items from props; or items loaded in a local state
  // from an API endpoint with useEffect and useState)
  const endOffset = itemOffset + itemsPerPage;
  console.log(`Loading items from ${itemOffset} to ${endOffset}`);
  const currentItems = data.slice(itemOffset, endOffset);
  const pageCount = Math.ceil(data.length / itemsPerPage);

  // Invoke when user click to request another page.
  const handlePageClick = (event) => {
    const newOffset = (event.selected * itemsPerPage) % data.length;
    console.log(
      `User requested page number ${event.selected}, which is offset ${newOffset}`
    );
    setItemOffset(newOffset);
  };

  return (
    <div id="container">
      {listItemOption == "submission" ? (
        <SubmissionItems currentItems={currentItems} />
      ) : (
        <StudentItems currentItems={currentItems} />
      )}
      <ReactPaginate
        className="paginate"
        breakLabel="..."
        nextLabel="next >"
        onPageChange={handlePageClick}
        pageRangeDisplayed={5}
        pageCount={pageCount}
        previousLabel="< previous"
        renderOnZeroPageCount={null}
      />
    </div>
  );
}

// Add a <div id="container"> to your HTML to see the component rendered.
/*
ReactDOM.render(
  <PaginatedItems itemsPerPage={4} />,
  document.getElementById("container")
);
*/

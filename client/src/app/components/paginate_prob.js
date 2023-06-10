import React, { useEffect, useState } from "react";
import Link from "next/link";
import ReactPaginate from "react-paginate";
import "./paginate_prob.css";

// Example items, to simulate fetching from another resources.

function Items({ currentItems }) {
  console.log("currentItems");
  console.log(currentItems);
  return (
    <div className="items-container">
      <table className="items-table">
        <thead className="item-header">
          <tr>
            <th style={{ width: "8%" }} data-sort="int">
              #
            </th>
            <th style={{ width: "12%" }} data-sort="int">
              문제 번호
            </th>
            <th style={{ width: "20%" }} data-sort="string">
              제목
            </th>
            <th style={{ width: "60%" }} data-sort="string">
              링크
            </th>
          </tr>
        </thead>
        <tbody>
          {currentItems.map((item, idx) => (
            <tr>
              <td>{item.id}</td>
              <td>
                <Link href={`study/student/problem/${item.id}`}>
                  {item.pNumber}
                </Link>
              </td>
              <td>
                <Link href={`study/student/problem/${item.id}`}>
                  {item.title}
                </Link>
              </td>
              <td>
                <Link href={`study/student/problem/${item.id}`}>
                  {item.link}
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default function PaginatedItems({ itemsPerPage, data }) {
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
      <Items currentItems={currentItems} />
      <ReactPaginate
        className="prob-paginate"
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

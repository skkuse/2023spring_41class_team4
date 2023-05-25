import Link from "next/link";
import "./page.css";

export default function ProblemItem({ params }) {
  return <>{params.id}</>;
}

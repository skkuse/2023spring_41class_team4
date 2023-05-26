import Link from "next/link";
import "./header.css";

export default function Header() {
  return (
    <div className="header-flex-row header">
      <div className="header-flex-row gap">
        <Link href="/" className="text pointer">
          Home
        </Link>
        <Link href="/study" className="text pointer">
          Study
        </Link>
      </div>
      <Link href="/" className="pointer title">
        Codemy
      </Link>
      <Link href="/login" className="login-box pointer">
        Login
      </Link>
    </div>
  );
}

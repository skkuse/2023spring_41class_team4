import Link from "next/link";
import "./footer.css";

export default function Footer() {
  return (
    <footer className="footer">
      <div className="container">
        <div className="flex-row">
          <span className="opacity4">2023 Software Engineering Team4</span>
          <h2 className="title">Codemy</h2>
        </div>
        <div className="line"></div>
        <div className="flex-row">
          <div className="flex-row gap">
            <Link href="/" className="opacity8 text">
              Home
            </Link>
            <Link href="/study" className="opacity8 text">
              Study
            </Link>
          </div>
          <a href="https://github.com/skkuse/2023spring_41class_team4">
            Github
          </a>
        </div>
      </div>
    </footer>
  );
}

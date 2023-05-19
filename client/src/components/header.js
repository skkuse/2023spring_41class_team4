import "./header.css";

export default function Header() {
  return (
    <div className="flex-col header">
      <div className="flex-col gap">
        <div className="text pointer">Home</div>
        <div className="text pointer">Study</div>
      </div>
      <h1 className="pointer">Codemy</h1>
      <div className="login-box pointer">Login</div>
    </div>
  );
}

import Link from "next/link";
import Image from 'next/image';
import "./page.css";

export default function Register() {
  return (
    <main>    
      <div class="containerDiv">
      <h3 id="pageTitle">ㅇㅇㅇ</h3>
      <div class="signupDiv">
        <div class="classBox">
        <Link href="/register/student"><Image src="/student.png" width={100} height={100}/></Link>
        <p>학생</p>
        </div>  
        <div class="classBox">
        <Link href="/register/instructor"><Image src="/teacher.png"  id="teacherBox" width={100} height={100}/></Link>
        <p>강사</p>
        </div>
      </div>
    </div>
    </main>
  );
}

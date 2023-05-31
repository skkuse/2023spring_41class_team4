import "./page.css";
import Image from 'next/image';

export default function Home() {
  return (
    <main>
      <div class = "mainClass">
        <div class="mainText">
          <p class="titleText">ABOUT US</p>
          <p class="aboutText">Codemy는 프로그래밍 문제를 풀고 강사에게서 코멘트와 개선점을 시각화 하여 확인할 수 있는 학원 코딩 교육 서비스이다. 제출된 코드는 ChatGPT에게서 1차 피드백을 받고 이를 통해 강사는 코멘트와 개선점을 작성하여 학생에게 전달한다.</p>
          <p class="titleText">VISION</p>
          <p class="visionText">Codemy는 서비스를 제공함으로써 학생들의 코딩 역량을 함양하는 것을 목표로 하고 있습니다. ChatGPT를 통해 얻은 객관적인 데이터를 기반으로 시각화된 자료를 활용하여, 앞으로의 방향성과 다양한 관점에서의 솔루션을 고려하며 개인의 능력 향상에 집중하고 있습니다.</p>       
        </div>
        <Image src="/main_page.png"  class="mainImage" width={350} height={350} alt="main image"/>
      </div>




      
    </main>
  );
}

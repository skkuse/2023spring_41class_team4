import styles from "./globals.css";
import { Inter } from "next/font/google";
import Header from "./components/header";
import Footer from "./components/footer";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "Create Next App",
  description: "Generated by create next app",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <Header></Header>
      <div className="content">
        <div className={styles}>{children}</div>
      </div>
      <Footer></Footer>
    </html>
  );
}

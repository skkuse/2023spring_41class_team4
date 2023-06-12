import styles from "./globals.css";
import { Inter } from "next/font/google";
import Header from "@/app/components/header";
import Footer from "@/app/components/footer";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "Codemy",
  description: "Next generation coding education service",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head>
        <title>{metadata.title}</title>
      </head>
      <body>
        <Header></Header>
        <div className="content">
          <div className={styles}>{children}</div>
        </div>
        <Footer></Footer>
      </body>
    </html>
  );
}

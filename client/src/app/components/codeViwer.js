import Prism from "prismjs";
import "prismjs/themes/prism-tomorrow.css";
import "prismjs/components/prism-c";
import "prismjs/components/prism-cpp";
import { useEffect } from "react";

export default function codeViwer({ code, language }) {
  useEffect(() => {
    Prism.highlightAll();
  }, [code, language]);
  return (
    <>
      <div className="Code">
        <pre>
          <code className={"language-" + language}>
            {code != undefined && code}
          </code>
        </pre>
      </div>
    </>
  );
}

import Prism from 'prismjs';
import React, { useEffect, useRef } from 'react';
import 'prismjs/themes/prism.css';
import 'prismjs/components/prism-javascript';

const CodeHighlighter = ({ code }) => {
  const textareaRef = useRef(null);

  useEffect(() => {
    if (textareaRef.current) {
      const codeBlock = document.createElement('code');
      codeBlock.textContent = code;
      codeBlock.className = 'language-javascript';

      const pre = document.createElement('pre');
      pre.appendChild(codeBlock);

      textareaRef.current.style.display = 'none'; // Hide the textarea
      textareaRef.current.parentNode.insertBefore(pre, textareaRef.current);

      Prism.highlightElement(codeBlock);
    }
  }, [code]);

  return (
    <div>
      {/* <textarea ref={textareaRef} value={code} readOnly /> */}
      <textfiled ref={textareaRef} value={code} />
    </div>
  );
};

export default CodeHighlighter;
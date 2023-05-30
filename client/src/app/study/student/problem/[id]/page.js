"use client"
// import CodeMirror from '@uiw/react-codemirror';
// import { createTheme } from '@uiw/codemirror-themes';
// import { javascript } from '@codemirror/lang-javascript';
// import { tags as t } from '@lezer/highlight';
// import "./page.css";



export default function ProblemItem({ params }) {
//   const code = `#include<bits/stdc++.h>
// using namespace std;
  
// int main(){
//   cout << "testing";

//   return 0;
// }
//   `;
//   return (
//     <CodeEditor code={code}></CodeEditor>

//   );

  return <>{params.id}</>;
}

// const myTheme = createTheme({
//   theme: 'light',
//   settings: {
//     background: '#ffffff',
//     foreground: '#75baff',
//     caret: '#5d00ff',
//     selection: '#036dd626',
//     selectionMatch: '#036dd626',
//     lineHighlight: '#8a91991a',
//     gutterBackground: '#fff',
//     gutterForeground: '#8a919966',
//   },
//   styles: [
//     { tag: t.comment, color: '#787b8099' },
//     { tag: t.variableName, color: '#0080ff' },
//     { tag: [t.string, t.special(t.brace)], color: '#5c6166' },
//     { tag: t.number, color: '#5c6166' },
//     { tag: t.bool, color: '#5c6166' },
//     { tag: t.null, color: '#5c6166' },
//     { tag: t.keyword, color: '#5c6166' },
//     { tag: t.operator, color: '#5c6166' },
//     { tag: t.className, color: '#5c6166' },
//     { tag: t.definition(t.typeName), color: '#5c6166' },
//     { tag: t.typeName, color: '#5c6166' },
//     { tag: t.angleBracket, color: '#5c6166' },
//     { tag: t.tagName, color: '#5c6166' },
//     { tag: t.attributeName, color: '#5c6166' },
//   ],
// });
// const extensions = [javascript({ jsx: true })];

// export default function ProblemItem({ params}) {
//   const onChange = React.useCallback((value, viewUpdate) => {
//     console.log('value:', value);
//   }, []);
//   return (
//     <CodeMirror
//       value="console.log('hello world!');"
//       height="200px"
//       theme={myTheme}
//       extensions={extensions}
//       onChange={onChange}
//     />
//   );
// }
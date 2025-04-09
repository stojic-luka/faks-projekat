// import CodeSnippetBox from "../components/home/codeSnippetBox";

// export function formatBotResponse(aiText: string) {
//   const lines = aiText.split("\n");

//   let inCodeBlock = false;
//   let currentLanguage: string | null = null;
//   let codeBlockContent: string[] = [];
//   let tableRows: JSX.Element[] = [];
//   let orderedList: JSX.Element[] = [];
//   let unorderedList: JSX.Element[] = [];
//   let inTable = false;

//   return lines
//     .map((line, index) => {
//       /**
//        * Code block
//        */
//       const codeBlockStartRegex = /^```(\w+)?$/;
//       const matchCodeBlockStart = line.match(codeBlockStartRegex);
//       if (matchCodeBlockStart) {
//         inCodeBlock = !inCodeBlock;

//         if (inCodeBlock) {
//           currentLanguage = matchCodeBlockStart[1] || "plaintext";
//           return null;
//         }

//         if (!inCodeBlock && currentLanguage) {
//           const content = codeBlockContent.join("\n");
//           codeBlockContent = [];

//           return (
//             <div key={index}>
//               <CodeSnippetBox currentLanguage={currentLanguage} content={content} />
//             </div>
//           );
//         }

//         return null;
//       }

//       if (inCodeBlock) {
//         codeBlockContent.push(line);
//         return null;
//       }

//       /**
//        * Headings
//        */
//       if (line.startsWith("### ")) return <h3 key={index}>{line.substring(4)}</h3>;
//       if (line.startsWith("## ")) return <h2 key={index}>{line.substring(3)}</h2>;
//       if (line.startsWith("# ")) return <h1 key={index}>{line.substring(2)}</h1>;

//       /**
//        * Horizontal rule
//        */
//       const horizontalRuleRegex = /^_{3,}$/; // Horizontal rule: ___
//       if (horizontalRuleRegex.test(line)) return <hr key={index} />;

//       /**
//        * Lists
//        */
//       if (/^\d+\.\s/.test(line)) {
//         orderedList.push(<li key={orderedList.length}>{line.replace(/^\d+\.\s/, "")}</li>);
//         return null;
//       }

//       if (/^(\*|-)\s/.test(line)) {
//         unorderedList.push(<li key={unorderedList.length}>{line.substring(2)}</li>);
//         return null;
//       }

//       if (orderedList.length > 0) {
//         const returnList = <ol key={`ordered-list-${index}`}>{orderedList}</ol>;
//         orderedList = [];
//         return returnList;
//       }

//       if (unorderedList.length > 0) {
//         const returnList = <ul key={`unordered-list-${index}`}>{unorderedList}</ul>;
//         unorderedList = [];
//         return returnList;
//       }

//       /**
//        * Tables
//        */
//       if (line.includes("|")) {
//         if (line.match(/^\|(-+\|)+$/)) return null;

//         const cells = line.split("|").filter((cell) => cell.trim() !== "");
//         if (cells.length > 0) {
//           inTable = true;
//           tableRows.push(
//             <tr key={tableRows.length}>
//               {cells.map((cell, i) => {
//                 const isPhoneNumber = /^\+?(\d{1,3})[- ]?(\d{1,4})[- ]?(\d{1,4})[- ]?(\d{1,4})$/.test(cell.trim());
//                 const CellTag = tableRows.length === 0 ? "th" : "td";
//                 return (
//                   <CellTag key={i} className={isPhoneNumber ? "whitespace-nowrap" : "whitespace-break-spaces"}>
//                     {cell.trim()}
//                   </CellTag>
//                 );
//               })}
//             </tr>
//           );
//           return null;
//         }
//       }

//       if (inTable) {
//         inTable = false;
//         const [header, ...body] = tableRows;
//         tableRows = [];

//         return (
//           <div key={`table-${index}`}>
//             <table>
//               <thead>{header}</thead>
//               <tbody>{body}</tbody>
//             </table>
//           </div>
//         );
//       }

//       if (line.trim() === "") return <br key={index} />;

//       const boldRegex = /\*\*(.*?)\*\*/g; // Bold: **text**
//       const italicRegex = /\*(.*?)\*/g; // Italic: *text*
//       const strikethroughRegex = /~~(.*?)~~/g; // Strikethrough: ~~text~~
//       const underlineRegex = /__(.*?)__/g; // Underlined: <u>text</u>
//       const inlineCodeRegex = /`(.*?)`/g; // Inline code: `code`
//       const superscriptRegex = /\^\{(.*?)\}/g; // Superscript: ^{text}
//       const subscriptRegex = /_\{(.*?)\}/g; // Subscript: _{text}
//       const linkRegex = /\[(.*?)\]\((.*?)\)/g; // Links: [text](url)
//       const imageRegex = /!\[(.*?)\]\((.*?)\)/g; // Images: ![alt](url)

//       let formattedLine = line;
//       formattedLine = formattedLine
//         .replace(boldRegex, (_, text) => `<strong>${text}</strong>`)
//         .replace(italicRegex, (_, text) => `<em>${text}</em>`)
//         .replace(strikethroughRegex, (_, text) => `<del>${text}</del>`)
//         .replace(underlineRegex, (_, text) => `<u>${text}</u>`)
//         .replace(inlineCodeRegex, (_, text) => `<code>${text}</code>`)
//         .replace(superscriptRegex, (_, text) => `<sup>${text}</sup>`)
//         .replace(subscriptRegex, (_, text) => `<sub>${text}</sub>`)
//         .replace(linkRegex, (_, text, url) => `<a href="${url}" target="_blank">${text}</a>`)
//         .replace(imageRegex, (_, alt, url) => `<img src="${url}" alt="${alt}" style="max-width: 100%;" />`);

//       return (
//         <div key={index}>
//           <p dangerouslySetInnerHTML={{ __html: formattedLine }} />
//         </div>
//       );
//     })
//     .filter((value) => !!value);
// }


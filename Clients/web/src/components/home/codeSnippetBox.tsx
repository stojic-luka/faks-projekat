import { useRef } from "react";
import CodeCopyButton from "./codeCopyButton";

interface Props {
  currentLanguage: string;
  content: string;
}
const CodeSnippetBox = ({ currentLanguage, content }: Props) => {
  const codeBoxRef = useRef<HTMLPreElement>(null);

  return (
    <>
      <div>
        <span>{currentLanguage.charAt(0).toUpperCase() + currentLanguage.slice(1)}</span>
      </div>
      <div>
        <div>
          <CodeCopyButton codeBoxRef={codeBoxRef} />
        </div>
      </div>
      <pre ref={codeBoxRef}>{content}</pre>
    </>
  );
};

export default CodeSnippetBox;

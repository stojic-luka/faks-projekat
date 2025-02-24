import { forwardRef, Ref, useEffect, useState } from "react";

interface Props {
  codeBoxRef: Ref<HTMLPreElement>;
}
const CodeCopyButton = forwardRef<HTMLButtonElement, Props>((props, ref) => {
  const [copied, setCopied] = useState(false);

  useEffect(() => {
    if (copied) {
      setTimeout(() => {
        setCopied(false);
      }, 2000);
    }
  }, [copied]);

  const handleCopy = () => {
    if (props.codeBoxRef && "current" in props.codeBoxRef && props.codeBoxRef.current) {
      const code = props.codeBoxRef.current.textContent || "";
      navigator.clipboard.writeText(code);
      setCopied(true);
    }
  };

  return (
    <button ref={ref} onClick={handleCopy} disabled={copied}>
      {copied ? "Copied" : "Copy code"}
    </button>
  );
});

export default CodeCopyButton;

import { forwardRef, Ref, useEffect, useState } from "react";

interface CodeCopyButtonProps {
  codeBoxRef: Ref<HTMLPreElement>;
}
const CodeCopyButton = forwardRef<HTMLButtonElement, CodeCopyButtonProps>(({ codeBoxRef }, ref) => {
  const [copied, setCopied] = useState(false);

  useEffect(() => {
    if (copied) {
      const timer = setTimeout(() => setCopied(false), 2000);
      return () => clearTimeout(timer);
    }
  }, [copied]);

  const handleCopy = () => {
    if (codeBoxRef && "current" in codeBoxRef && codeBoxRef.current) {
      const code = codeBoxRef.current.textContent || "";
      navigator.clipboard.writeText(code);
      setCopied(true);
    }
  };

  return (
    <button
      ref={ref}
      onClick={handleCopy}
      disabled={copied}
      className="text-neutral-700 dark:text-neutral-300 border px-2 py-1 m-1 border-neutral-300 dark:border-neutral-600 rounded-lg text-xs hover:bg-neutral-100 dark:bg-neutral-800 dark:hover:bg-neutral-700 hover:text-neutral-900 dark:hover:text-neutral-100 transition-colors duration-150"
    >
      {copied ? "Copied" : "Copy code"}
    </button>
  );
});

export default CodeCopyButton;

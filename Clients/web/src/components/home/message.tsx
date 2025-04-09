import React, { memo, useRef } from "react";
import { useAuth } from "../../contexts/useAuth";
import { RoleTypes } from "../../types/chatTypes/chatEnums";
import { ChatMessage } from "../../types/chatTypes/chatModels";
import Markdown from "react-markdown";
import type { Components } from "react-markdown";
import rehypeHighlight from "rehype-highlight";
import "highlight.js/styles/atom-one-dark.min.css";
import CodeCopyButton from "./codeCopyButton";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export const CodeBlock: Components["pre"] = ({ node, className, children, ...props }) => {
  const codeBoxRef = useRef<HTMLPreElement>(null);
  const codeChild = React.Children.toArray(children).find((child) => {
    return React.isValidElement(child) && child.type === "code";
  }) as React.ReactElement | null;

  if (!codeChild) {
    return (
      <pre className={className} {...props}>
        {children}
      </pre>
    );
  }

  const codeClassName: string = codeChild.props.className || "";
  const match = /language-(\w+)/.exec(codeClassName);
  const language = match ? match[1] : "plaintext";

  return (
    <pre className={`relative hljs ${className || ""}`} {...props}>
      <div className="flex flex-row h-10 rounded-t-xl w-auto bg-neutral-100 dark:bg-neutral-800 border-b-4 border-neutral-200 dark:border-neutral-700">
        <span className="ml-3 my-auto text-xs">{language.charAt(0).toUpperCase() + language.slice(1)}</span>
      </div>
      <div className="sticky top-9 right-0 w-auto -mt-[3px] -mr-[1px]">
        <div className="absolute bottom-0.5 right-0.5 items-center">
          <CodeCopyButton codeBoxRef={codeBoxRef} />
        </div>
      </div>
      {React.cloneElement(codeChild, { ref: codeBoxRef })}
    </pre>
  );
};

const BotMessageBubble = ({ content }: { content: string }) => {
  return (
    <div className="flex w-full justify-start">
      <div className="flex flex-col max-w-full">
        <div className="ml-3 mb-2">
          <p className="text-xs text-gray-400">MeLLama</p>
        </div>
        <div className="leading-relaxed pr-4 pb-3 rounded-2xl text-white bot-reply">
          <Markdown rehypePlugins={[rehypeHighlight]} components={{ pre: CodeBlock }}>
            {content}
          </Markdown>
        </div>
      </div>
    </div>
  );
};

const UserMessageBubble = ({ content }: { content: string }) => {
  const { user } = useAuth();

  return (
    <div className="flex w-full justify-end">
      <div className="flex flex-col max-w-full mr-2">
        <div className="ml-auto mr-3 mb-2">
          <p className="text-xs text-gray-400">{user?.username || "You"}</p>
        </div>
        <div className="flex">
          <div className="leading-relaxed px-4 py-3 ml-auto rounded-2xl shadow-md bg-green-500 text-white whitespace-pre-wrap">{content}</div>
        </div>
      </div>
    </div>
  );
};

const MessageBubble = memo(({ message }: { message: ChatMessage }) => {
  return (
    <div className="flex w-full whitespace-pre-wrap">
      {message.role !== RoleTypes.ASSISTANT ? (
        <UserMessageBubble content={message.content.text} />
      ) : (
        <BotMessageBubble content={message.content.text} />
      )}
    </div>
  );
});

export default MessageBubble;

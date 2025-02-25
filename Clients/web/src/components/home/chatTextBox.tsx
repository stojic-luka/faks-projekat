import { useCallback, useEffect, useRef, useState } from "react";

import GearSvg from "../../assets/svg/gear.svg?react";
import SendSvg from "../../assets/svg/send.svg?react";
import SettingsMenu from "./settingsMenu";

interface Props {
  sendMessage: (message: string) => void;
  disabled: boolean;
  onAbort: ((reason?: unknown) => void) | undefined;
}
const ChatTextBox = ({ sendMessage, disabled }: Props) => {
  const [input, setInput] = useState("");
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const promptBoxRef = useRef<HTMLTextAreaElement>(null);

  const handleSendMessage = useCallback(() => {
    if (input.trim() !== "") {
      setInput("");
      sendMessage(input);
    }
  }, [input, sendMessage]);

  const handleOnKeyDown = useCallback(
    (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
      if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault();
        handleSendMessage();
      }
    },
    [handleSendMessage]
  );

  useEffect(() => {
    if (promptBoxRef.current) {
      promptBoxRef.current.style.height = "0px";
      promptBoxRef.current.style.height = `${promptBoxRef.current.scrollHeight}px`;
    }
  }, [input]);

  return (
    <>
      <div className="flex w-full cursor-text flex-col rounded-3xl px-2.5 py-1 bg-[#f4f4f4] dark:bg-[#2f2f2f]">
        <div className="flex min-h-[44px] items-start">
          <div className="relative flex h-[44px] items-center justify-between mt-auto">
            <button
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              aria-label="Attach files"
              className="flex items-center justify-center h-8 w-8 rounded-xl hover:bg-black/10 duration-75"
            >
              <GearSvg className="m-1 icon-stroke-dark dark:icon-stroke-light stroke-[1.5]" />
            </button>
            {isMenuOpen && (
              <div className="absolute left-1 bottom-10">
                <SettingsMenu />
              </div>
            )}
          </div>
          <div className="min-w-0 max-w-full flex-1 max-h-52 overflow-auto mx-2 scrollbar-redesign dark:scrollbar-redesign-dark">
            <textarea
              ref={promptBoxRef}
              className="block h-11 w-full resize-none border-none outline-none bg-transparent px-0 py-2 placeholder:text-neutral-400 text-neutral-700 dark:text-neutral-300"
              placeholder="Message MeLLama"
              onKeyDown={handleOnKeyDown}
              onChange={(e) => setInput(e.target.value)}
              value={input}
              disabled={disabled}
            />
          </div>
          <div className="flex h-[44px] items-center justify-between mt-auto">
            <button
              aria-label="Attach files"
              className="flex items-center justify-center h-8 w-8 rounded-xl hover:bg-black/10 duration-75"
              onClick={handleSendMessage}
            >
              <SendSvg className="m-1 icon-stroke-dark dark:icon-stroke-light stroke-[1.5]" />
              {/* <SquareSvg onClick={onAbort} /> */}
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default ChatTextBox;
